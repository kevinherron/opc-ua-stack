package com.digitalpetri.opcua.stack.client;

import java.net.URI;
import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import com.digitalpetri.opcua.stack.client.config.UaTcpStackClientConfig;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateContext;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateEvent;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateObserver;
import com.digitalpetri.opcua.stack.client.handlers.UaTcpClientAcknowledgeHandler;
import com.digitalpetri.opcua.stack.core.Stack;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.UaServiceFaultException;
import com.digitalpetri.opcua.stack.core.application.UaStackClient;
import com.digitalpetri.opcua.stack.core.channel.ChannelConfig;
import com.digitalpetri.opcua.stack.core.channel.ClientSecureChannel;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.ApplicationType;
import com.digitalpetri.opcua.stack.core.types.enumerated.MessageSecurityMode;
import com.digitalpetri.opcua.stack.core.types.structured.ApplicationDescription;
import com.digitalpetri.opcua.stack.core.types.structured.EndpointDescription;
import com.digitalpetri.opcua.stack.core.types.structured.FindServersRequest;
import com.digitalpetri.opcua.stack.core.types.structured.FindServersResponse;
import com.digitalpetri.opcua.stack.core.types.structured.GetEndpointsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.GetEndpointsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.RequestHeader;
import com.digitalpetri.opcua.stack.core.types.structured.ResponseHeader;
import com.digitalpetri.opcua.stack.core.types.structured.ServiceFault;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;
import static com.google.common.collect.Lists.newArrayList;

public class UaTcpStackClient implements UaStackClient {

    private static final long DEFAULT_TIMEOUT_MS = 60000;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<UInteger, CompletableFuture<UaResponseMessage>> pending = Maps.newConcurrentMap();
    private final Map<UInteger, Timeout> timeouts = Maps.newConcurrentMap();
    private final HashedWheelTimer wheelTimer = Stack.sharedWheelTimer();

    private volatile ClientSecureChannel secureChannel;

    private final ApplicationDescription application;
    private final ConnectionStateContext stateContext;

    private final UaTcpStackClientConfig config;

    public UaTcpStackClient(UaTcpStackClientConfig config) {
        this.config = config;

        application = new ApplicationDescription(
                config.getApplicationUri(),
                config.getProductUri(),
                config.getApplicationName(),
                ApplicationType.Client,
                null, null, null);

        secureChannel = new ClientSecureChannel(
                SecurityPolicy.None, MessageSecurityMode.None);

        stateContext = new ConnectionStateContext(this);
    }

    @Override
    public CompletableFuture<UaStackClient> connect() {
        CompletableFuture<UaStackClient> future = new CompletableFuture<>();

        if (!stateContext.isConnected()) {
            stateContext.handleEvent(ConnectionStateEvent.CONNECT_REQUESTED);
        }

        stateContext.getChannelFuture().whenComplete((ch, ex) -> {
            if (ch != null) future.complete(this);
            else future.completeExceptionally(ex);
        });

        return future;
    }

    @Override
    public CompletableFuture<UaStackClient> disconnect() {
        stateContext.handleEvent(ConnectionStateEvent.DISCONNECT_REQUESTED);

        return CompletableFuture.completedFuture(this);
    }

    @SuppressWarnings("unchecked")
    public <T extends UaResponseMessage> CompletableFuture<T> sendRequest(UaRequestMessage request) {
        if (!stateContext.isConnected()) {
            stateContext.handleEvent(ConnectionStateEvent.CONNECT_REQUESTED);
        }

        return stateContext.getChannelFuture().thenCompose(ch -> {
            CompletableFuture<T> future = new CompletableFuture<>();

            RequestHeader requestHeader = request.getRequestHeader();

            pending.put(requestHeader.getRequestHandle(), (CompletableFuture<UaResponseMessage>) future);

            scheduleRequestTimeout(requestHeader);

            ch.writeAndFlush(request).addListener(f -> {
                if (!f.isSuccess()) {
                    UInteger requestHandle = request.getRequestHeader().getRequestHandle();

                    pending.remove(requestHandle);
                    future.completeExceptionally(f.cause());

                    logger.debug("Write failed, requestHandle={}", requestHandle, f.cause());
                }
            });

            return future;
        });
    }

    @SuppressWarnings("unchecked")
    public void sendRequests(List<? extends UaRequestMessage> requests,
                             List<CompletableFuture<? extends UaResponseMessage>> futures) {

        Preconditions.checkArgument(requests.size() == futures.size(),
                "requests and futures parameters must be same size");

        if (!stateContext.isConnected()) {
            stateContext.handleEvent(ConnectionStateEvent.CONNECT_REQUESTED);
        }

        stateContext.getChannelFuture().whenComplete((ch, ex) -> {
            if (ch != null) {
                Iterator<? extends UaRequestMessage> requestIterator = requests.iterator();
                Iterator<CompletableFuture<? extends UaResponseMessage>> futureIterator = futures.iterator();

                while (requestIterator.hasNext() && futureIterator.hasNext()) {
                    UaRequestMessage request = requestIterator.next();
                    CompletableFuture<UaResponseMessage> future =
                            (CompletableFuture<UaResponseMessage>) futureIterator.next();

                    RequestHeader requestHeader = request.getRequestHeader();

                    pending.put(requestHeader.getRequestHandle(), future);

                    scheduleRequestTimeout(requestHeader);
                }

                ch.eventLoop().execute(() -> {
                    for (UaRequestMessage request : requests) {
                        ch.write(request).addListener(f -> {
                            if (!f.isSuccess()) {
                                UInteger requestHandle = request.getRequestHeader().getRequestHandle();

                                CompletableFuture<?> future = pending.remove(requestHandle);
                                if (future != null) future.completeExceptionally(f.cause());

                                logger.debug("Write failed, requestHandle={}", requestHandle, f.cause());
                            }
                        });
                    }

                    ch.flush();
                });
            } else {
                futures.forEach(f -> f.completeExceptionally(ex));
            }
        });
    }

    CompletableFuture<Channel> getChannelFuture() {
        return stateContext.getChannelFuture();
    }

    public void addStateObserver(ConnectionStateObserver observer) {
        stateContext.addStateObserver(observer);
    }

    public void removeStateObserver(ConnectionStateObserver observer) {
        stateContext.removeStateObserver(observer);
    }

    private void scheduleRequestTimeout(RequestHeader requestHeader) {
        UInteger requestHandle = requestHeader.getRequestHandle();

        long timeoutHint = requestHeader.getTimeoutHint() != null ?
                requestHeader.getTimeoutHint().longValue() : DEFAULT_TIMEOUT_MS;

        Timeout timeout = wheelTimer.newTimeout(t -> {
            timeouts.remove(requestHandle);
            if (!t.isCancelled()) {
                CompletableFuture<UaResponseMessage> f = pending.remove(requestHandle);
                if (f != null) {
                    String message = "request timed out after " + timeoutHint + "ms";
                    f.completeExceptionally(new UaException(StatusCodes.Bad_Timeout, message));
                }
            }
        }, timeoutHint, TimeUnit.MILLISECONDS);

        timeouts.put(requestHandle, timeout);
    }

    private void onChannelRead(ChannelHandlerContext ctx, UaResponseMessage msg) throws Exception {
        if (msg instanceof ServiceFault) {
            receiveServiceFault((ServiceFault) msg);
        } else {
            receiveServiceResponse(msg);
        }
    }

    private void onChannelInactive(ChannelHandlerContext ctx) {
        logger.debug("Channel inactive: {}", ctx.channel());
        stateContext.handleEvent(ConnectionStateEvent.ERR_CONNECTION_LOST);
    }

    private void onExceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("Exception caught: {}", cause.getMessage(), cause);
        ctx.close();
    }

    public void receiveServiceResponse(UaResponseMessage response) {
        ResponseHeader header = response.getResponseHeader();

        if (header.getServiceResult().isGood()) {
            UInteger requestHandle = header.getRequestHandle();

            CompletableFuture<UaResponseMessage> future = pending.remove(requestHandle);

            if (future != null) {
                future.complete(response);
            }

            Timeout timeout = timeouts.remove(requestHandle);
            if (timeout != null) timeout.cancel();
        } else {
            ServiceFault serviceFault = new ServiceFault(response.getResponseHeader());
            receiveServiceFault(serviceFault);
        }
    }

    public void receiveServiceFault(ServiceFault serviceFault) {
        ResponseHeader header = serviceFault.getResponseHeader();
        UInteger requestHandle = header.getRequestHandle();

        CompletableFuture<UaResponseMessage> future = pending.remove(requestHandle);

        if (future != null) {
            StatusCode serviceResult = serviceFault.getResponseHeader().getServiceResult();
            UaServiceFaultException exception = new UaServiceFaultException(
                    serviceFault, "service fault, serviceResult=" + serviceResult);

            future.completeExceptionally(exception);
        }

        Timeout timeout = timeouts.remove(requestHandle);
        if (timeout != null) timeout.cancel();
    }

    @Override
    public Optional<X509Certificate> getCertificate() {
        return config.getCertificate();
    }

    @Override
    public Optional<KeyPair> getKeyPair() {
        return config.getKeyPair();
    }

    @Override
    public ChannelConfig getChannelConfig() {
        return config.getChannelConfig();
    }

    @Override
    public UInteger getChannelLifetime() {
        return config.getChannelLifetime();
    }

    @Override
    public ClientSecureChannel getSecureChannel() {
        return secureChannel;
    }

    @Override
    public ApplicationDescription getApplication() {
        return application;
    }

    @Override
    public Optional<EndpointDescription> getEndpoint() {
        return config.getEndpoint();
    }

    @Override
    public String getEndpointUrl() {
        return config.getEndpoint()
                .map(EndpointDescription::getEndpointUrl)
                .orElse(config.getEndpointUrl().orElse(""));
    }

    @Override
    public ExecutorService getExecutorService() {
        return config.getExecutor();
    }

    public void setSecureChannel(ClientSecureChannel secureChannel) {
        this.secureChannel = secureChannel;
    }

    public static CompletableFuture<Channel> bootstrap(UaTcpStackClient client) {
        CompletableFuture<Channel> handshake = new CompletableFuture<>();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(Stack.sharedEventLoop())
                .channel(NioSocketChannel.class)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new UaTcpClientAcknowledgeHandler(client, handshake));
                        channel.pipeline().addLast(new UaTcpClientHandler(client));
                    }
                });

        try {
            URI uri = URI.create(client.getEndpointUrl());

            bootstrap.connect(uri.getHost(), uri.getPort()).addListener((ChannelFuture f) -> {
                if (!f.isSuccess()) {
                    handshake.completeExceptionally(f.cause());
                }
            });
        } catch (Throwable t) {
            UaException failure = new UaException(
                    StatusCodes.Bad_TcpEndpointUrlInvalid,
                    "endpoint URL invalid: " + client.getEndpointUrl());

            handshake.completeExceptionally(failure);
        }

        return handshake;
    }

    public static class UaTcpClientHandler extends SimpleChannelInboundHandler<UaResponseMessage> {

        private final UaTcpStackClient client;

        private UaTcpClientHandler(UaTcpStackClient client) {
            this.client = client;
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, UaResponseMessage msg) throws Exception {
            client.onChannelRead(ctx, msg);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            if (ctx.pipeline().context(UaTcpClientHandler.class) != null) {
                ctx.pipeline().remove(UaTcpClientHandler.class);
            }

            client.onChannelInactive(ctx);

            super.channelInactive(ctx);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            client.onExceptionCaught(ctx, cause);
        }

        public void secureChannelError(ChannelHandlerContext ctx, StatusCode errorCode) {
            List<CompletableFuture<UaResponseMessage>> futures = newArrayList(client.pending.values());
            futures.forEach(f -> f.completeExceptionally(new UaException(errorCode)));
            client.pending.clear();
        }

    }

    /**
     * Query the FindServers service at the given endpoint URL.
     * <p>
     * The endpoint URL(s) for each server {@link ApplicationDescription} returned can then be used in a
     * {@link #getEndpoints(String)} call to discover the endpoints for that server.
     *
     * @param endpointUrl the endpoint URL to find servers at.
     * @return the {@link ApplicationDescription}s returned by the FindServers service.
     */
    public static CompletableFuture<ApplicationDescription[]> findServers(String endpointUrl) {
        UaTcpStackClientConfig config = UaTcpStackClientConfig.builder()
                .setEndpointUrl(endpointUrl)
                .build();

        UaTcpStackClient client = new UaTcpStackClient(config);

        FindServersRequest request = new FindServersRequest(
                new RequestHeader(null, DateTime.now(), uint(1), uint(0), null, uint(5000), null),
                endpointUrl, null, null);

        return client.<FindServersResponse>sendRequest(request)
                .whenComplete((r, ex) -> client.disconnect())
                .thenApply(FindServersResponse::getServers);
    }

    /**
     * Query the GetEndpoints service at the given endpoint URL.
     *
     * @param endpointUrl the endpoint URL to get endpoints from.
     * @return the {@link EndpointDescription}s returned by the GetEndpoints service.
     */
    public static CompletableFuture<EndpointDescription[]> getEndpoints(String endpointUrl) {
        UaTcpStackClientConfig config = UaTcpStackClientConfig.builder()
                .setEndpointUrl(endpointUrl)
                .build();

        UaTcpStackClient client = new UaTcpStackClient(config);

        GetEndpointsRequest request = new GetEndpointsRequest(
                new RequestHeader(null, DateTime.now(), uint(1), uint(0), null, uint(5000), null),
                endpointUrl, null, new String[]{Stack.UA_TCP_BINARY_TRANSPORT_URI});

        return client.<GetEndpointsResponse>sendRequest(request)
                .whenComplete((r, ex) -> client.disconnect())
                .thenApply(GetEndpointsResponse::getEndpoints);
    }

}
