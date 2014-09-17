package com.digitalpetri.opcua.stack.client;

import java.net.URI;
import java.security.KeyPair;
import java.security.cert.Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateContext;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateEvent;
import com.digitalpetri.opcua.stack.client.fsm.states.ConnectionState;
import com.digitalpetri.opcua.stack.client.handlers.UaTcpClientAcknowledgeHandler;
import com.digitalpetri.opcua.stack.core.Stack;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.application.UaClient;
import com.digitalpetri.opcua.stack.core.channel.ChannelConfig;
import com.digitalpetri.opcua.stack.core.channel.ClientSecureChannel;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.enumerated.MessageSecurityMode;
import com.digitalpetri.opcua.stack.core.types.structured.ApplicationDescription;
import com.digitalpetri.opcua.stack.core.types.structured.EndpointDescription;
import com.digitalpetri.opcua.stack.core.types.structured.GetEndpointsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.GetEndpointsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.RequestHeader;
import com.digitalpetri.opcua.stack.core.types.structured.ResponseHeader;
import com.digitalpetri.opcua.stack.core.types.structured.ServiceFault;
import com.digitalpetri.opcua.stack.core.util.CertificateUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
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

public class UaTcpClient implements UaClient {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ClientSecureChannel secureChannel;
    private final HashedWheelTimer wheelTimer = Stack.WHEEL_TIMER;

    private final Map<Long, CompletableFuture<UaResponseMessage>> pending = Maps.newConcurrentMap();
    private final Map<Long, Timeout> timeouts = Maps.newConcurrentMap();

    private final ConnectionStateContext stateContext = new ConnectionStateContext(this);

    private final Optional<Certificate> certificate;
    private final Optional<KeyPair> keyPair;

    private final ApplicationDescription application;
    private final String endpointUrl;
    private final long requestTimeout;
    private final ChannelConfig channelConfig;
    private final ExecutorService executor;

    public UaTcpClient(ApplicationDescription application,
                       String endpointUrl,
                       long requestTimeout,
                       ChannelConfig channelConfig,
                       ExecutorService executor) {

        this.application = application;
        this.endpointUrl = endpointUrl;
        this.requestTimeout = requestTimeout;
        this.channelConfig = channelConfig;
        this.executor = executor;

        certificate = Optional.empty();
        keyPair = Optional.empty();

        secureChannel = new ClientSecureChannel(SecurityPolicy.None, MessageSecurityMode.None);
    }

    public UaTcpClient(ApplicationDescription application,
                       EndpointDescription endpoint,
                       KeyPair keyPair,
                       Certificate certificate,
                       long requestTimeout,
                       ChannelConfig channelConfig,
                       ExecutorService executor) throws UaException {

        this.application = application;
        this.endpointUrl = endpoint.getEndpointUrl();
        this.requestTimeout = requestTimeout;
        this.channelConfig = channelConfig;
        this.executor = executor;

        this.certificate = Optional.ofNullable(certificate);
        this.keyPair = Optional.ofNullable(keyPair);

        Certificate remoteCertificate = null;
        if (!endpoint.getServerCertificate().isNull()) {
            byte[] bs = endpoint.getServerCertificate().bytes();
            remoteCertificate = CertificateUtil.decode(bs);
        }

        secureChannel = new ClientSecureChannel(
                keyPair,
                certificate,
                remoteCertificate,
                SecurityPolicy.fromUri(endpoint.getSecurityPolicyUri()),
                endpoint.getSecurityMode()
        );
    }

    @Override
    public CompletableFuture<UaClient> connect() {
        CompletableFuture<UaClient> future = new CompletableFuture<>();

        ConnectionState state = stateContext.handleEvent(ConnectionStateEvent.ConnectRequested);

        state.getChannelFuture().whenComplete((ch, ex) -> {
            if (ch != null) future.complete(this);
            else future.completeExceptionally(ex);
        });

        return future;
    }

    @Override
    public CompletableFuture<UaClient> disconnect() {
        stateContext.handleEvent(ConnectionStateEvent.DisconnectRequested);

        return CompletableFuture.completedFuture(this);
    }

    @SuppressWarnings("unchecked")
    public <T extends UaResponseMessage> CompletableFuture<T> sendRequest(UaRequestMessage request) {
        CompletableFuture<T> future = new CompletableFuture<>();

        CompletableFuture<Channel> channelFuture =
                stateContext.handleEvent(ConnectionStateEvent.ConnectRequested).getChannelFuture();

        channelFuture.whenComplete((ch, ex) -> {
            if (ch != null) {
                Long requestHandle = request.getRequestHeader().getRequestHandle();

                Timeout timeout = wheelTimer.newTimeout(t -> {
                    timeouts.remove(requestHandle);
                    CompletableFuture<UaResponseMessage> f = pending.remove(requestHandle);
                    if (f != null) f.completeExceptionally(new UaException(StatusCodes.Bad_Timeout, "timeout"));
                }, requestTimeout, TimeUnit.MILLISECONDS);

                timeouts.put(requestHandle, timeout);

                pending.put(requestHandle, (CompletableFuture<UaResponseMessage>) future);

                ch.writeAndFlush(request, ch.voidPromise());
            } else {
                future.completeExceptionally(ex);
            }
        });

        return future;
    }

    @SuppressWarnings("unchecked")
    public void sendRequests(List<? extends UaRequestMessage> requests,
                             List<CompletableFuture<? extends UaResponseMessage>> futures) {

        Preconditions.checkArgument(requests.size() == futures.size(), "requests and futures parameters must be same size");

        CompletableFuture<Channel> channelFuture =
                stateContext.handleEvent(ConnectionStateEvent.ConnectRequested).getChannelFuture();

        channelFuture.whenComplete((ch, ex) -> {
            if (ch != null) {
                Iterator<? extends UaRequestMessage> requestIterator = requests.iterator();
                Iterator<CompletableFuture<? extends UaResponseMessage>> futureIterator = futures.iterator();

                while (requestIterator.hasNext() && futureIterator.hasNext()) {
                    UaRequestMessage request = requestIterator.next();
                    CompletableFuture<UaResponseMessage> future =
                            (CompletableFuture<UaResponseMessage>) futureIterator.next();

                    Long requestHandle = request.getRequestHeader().getRequestHandle();

                    Timeout timeout = wheelTimer.newTimeout(t -> {
                        timeouts.remove(requestHandle);
                        CompletableFuture<UaResponseMessage> f = pending.remove(requestHandle);
                        if (f != null) f.completeExceptionally(new UaException(StatusCodes.Bad_Timeout, "timeout"));
                    }, requestTimeout, TimeUnit.MILLISECONDS);

                    timeouts.put(requestHandle, timeout);

                    pending.put(requestHandle, future);
                }

                ch.eventLoop().execute(() -> {
                    requests.forEach(r -> ch.write(r, ch.voidPromise()));
                    ch.flush();
                });
            } else {
                futures.forEach(f -> f.completeExceptionally(ex));
            }
        });
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
        stateContext.handleEvent(ConnectionStateEvent.ConnectionLost);
    }

    private void onExceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("Exception caught: {}", cause.getMessage(), cause);
        stateContext.handleEvent(ConnectionStateEvent.ConnectionLost);
        ctx.close();
    }

    public void receiveServiceResponse(UaResponseMessage response) {
        ResponseHeader header = response.getResponseHeader();
        Long requestHandle = header.getRequestHandle();

        CompletableFuture<UaResponseMessage> future = pending.remove(requestHandle);

        if (future != null) {
            future.complete(response);
        }

        Timeout timeout = timeouts.remove(requestHandle);
        if (timeout != null) timeout.cancel();
    }

    public void receiveServiceFault(ServiceFault serviceFault) {
        ResponseHeader header = serviceFault.getResponseHeader();
        Long requestHandle = header.getRequestHandle();

        CompletableFuture<UaResponseMessage> future = pending.remove(requestHandle);

        if (future != null) {
            StatusCode serviceResult = serviceFault.getResponseHeader().getServiceResult();

            future.completeExceptionally(new UaException(serviceResult.getValue(), "service fault"));
        }

        Timeout timeout = timeouts.remove(requestHandle);
        if (timeout != null) timeout.cancel();
    }

    @Override
    public Optional<Certificate> getCertificate() {
        return certificate;
    }

    @Override
    public Optional<KeyPair> getKeyPair() {
        return keyPair;
    }

    @Override
    public ChannelConfig getChannelConfig() {
        return channelConfig;
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
    public String getEndpointUrl() {
        return endpointUrl;
    }

    @Override
    public long getRequestTimeout() {
        return requestTimeout;
    }

    @Override
    public ExecutorService getExecutorService() {
        return executor;
    }

    public static CompletableFuture<Channel> bootstrap(UaTcpClient client) {
        CompletableFuture<Channel> handshake = new CompletableFuture<>();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(Stack.EVENT_LOOP)
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

        URI uri = URI.create(client.getEndpointUrl());

        bootstrap.connect(uri.getHost(), uri.getPort()).addListener(f -> {
            if (!f.isSuccess()) {
                handshake.completeExceptionally(f.cause());
            }
        });

        return handshake;
    }

    private static class UaTcpClientHandler extends SimpleChannelInboundHandler<UaResponseMessage> {

        private final UaTcpClient client;

        private UaTcpClientHandler(UaTcpClient client) {
            this.client = client;
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, UaResponseMessage msg) throws Exception {
            client.onChannelRead(ctx, msg);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            client.onChannelInactive(ctx);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            client.onExceptionCaught(ctx, cause);
        }
    }

    /**
     * Query the GetEndpoints service at the given endpoint URL.
     *
     * @param endpointUrl the endpoint URL to get endpoints from.
     * @return the {@link EndpointDescription}s returned by the GetEndpoints service.
     */
    public static CompletableFuture<EndpointDescription[]> getEndpoints(String endpointUrl) {
        CompletableFuture<EndpointDescription[]> endpointsFuture = new CompletableFuture<>();

        UaTcpClient client = new UaTcpClientBuilder().build(endpointUrl);

        GetEndpointsRequest request = new GetEndpointsRequest(
                new RequestHeader(null, DateTime.now(), 1L, 0L, null, 5000L, null),
                endpointUrl, null, new String[]{Stack.UA_TCP_BINARY_TRANSPORT_URI}
        );

        client.sendRequest(request).whenComplete((r, ex) -> {
            GetEndpointsResponse response = (GetEndpointsResponse) r;

            if (response != null) endpointsFuture.complete(response.getEndpoints());
            else endpointsFuture.completeExceptionally(ex);

            client.disconnect();
        });

        return endpointsFuture;
    }

}
