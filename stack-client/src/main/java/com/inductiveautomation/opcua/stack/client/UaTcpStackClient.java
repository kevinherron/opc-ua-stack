package com.inductiveautomation.opcua.stack.client;

import java.net.URI;
import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.inductiveautomation.opcua.stack.client.fsm.ConnectionStateContext;
import com.inductiveautomation.opcua.stack.client.fsm.ConnectionStateEvent;
import com.inductiveautomation.opcua.stack.client.fsm.states.ConnectionState;
import com.inductiveautomation.opcua.stack.client.handlers.UaTcpClientAcknowledgeHandler;
import com.inductiveautomation.opcua.stack.core.Stack;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.application.UaStackClient;
import com.inductiveautomation.opcua.stack.core.channel.ChannelConfig;
import com.inductiveautomation.opcua.stack.core.channel.ClientSecureChannel;
import com.inductiveautomation.opcua.stack.core.security.SecurityPolicy;
import com.inductiveautomation.opcua.stack.core.serialization.UaRequestMessage;
import com.inductiveautomation.opcua.stack.core.serialization.UaResponseMessage;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.ApplicationType;
import com.inductiveautomation.opcua.stack.core.types.enumerated.MessageSecurityMode;
import com.inductiveautomation.opcua.stack.core.types.structured.ApplicationDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.EndpointDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.GetEndpointsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.GetEndpointsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.RequestHeader;
import com.inductiveautomation.opcua.stack.core.types.structured.ResponseHeader;
import com.inductiveautomation.opcua.stack.core.types.structured.ServiceFault;
import com.inductiveautomation.opcua.stack.core.util.CertificateUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class UaTcpStackClient implements UaStackClient {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ClientSecureChannel secureChannel;

    private final Map<Long, CompletableFuture<UaResponseMessage>> pending = Maps.newConcurrentMap();

    private final ConnectionStateContext stateContext = new ConnectionStateContext(this);

    private final ApplicationDescription application;
    private final UaTcpClientConfig config;

    public UaTcpStackClient(UaTcpClientConfig config) {
        this.config = config;

        application = new ApplicationDescription(
                config.getApplicationUri(),
                config.getProductUri(),
                config.getApplicationName(),
                ApplicationType.Client,
                null, null, null);

        secureChannel = config.getEndpoint().map(endpoint -> {
            ClientSecureChannel secureChannel = new ClientSecureChannel(
                    SecurityPolicy.None, MessageSecurityMode.None);

            X509Certificate remoteCertificate = null;
            List<X509Certificate> remoteCertificateChain = null;

            if (!endpoint.getServerCertificate().isNull()) {
                try {
                    byte[] bs = endpoint.getServerCertificate().bytes();
                    remoteCertificate = CertificateUtil.decodeCertificate(bs);
                    remoteCertificateChain = CertificateUtil.decodeCertificates(bs);
                    SecurityPolicy securityPolicy = SecurityPolicy.fromUri(endpoint.getSecurityPolicyUri());

                    secureChannel = new ClientSecureChannel(
                            config.getKeyPair().orElse(null),
                            config.getCertificate().orElse(null),
                            remoteCertificate,
                            remoteCertificateChain,
                            securityPolicy,
                            endpoint.getSecurityMode());
                } catch (UaException e) {
                    logger.warn("Unable to create ClientSecureChannel: {}",
                            e.getMessage(), e);
                }
            }

            return secureChannel;
        }).orElse(new ClientSecureChannel(SecurityPolicy.None, MessageSecurityMode.None));
    }

    @Override
    public CompletableFuture<UaStackClient> connect() {
        CompletableFuture<UaStackClient> future = new CompletableFuture<>();

        ConnectionState state = stateContext.handleEvent(ConnectionStateEvent.ConnectRequested);

        state.getChannelFuture().whenComplete((ch, ex) -> {
            if (ch != null) future.complete(this);
            else future.completeExceptionally(ex);
        });

        return future;
    }

    @Override
    public CompletableFuture<UaStackClient> disconnect() {
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
                long requestHandle = request.getRequestHeader().getRequestHandle().longValue();

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

                    long requestHandle = request.getRequestHeader().getRequestHandle().longValue();

//                    Timeout timeout = wheelTimer.newTimeout(t -> {
//                        timeouts.remove(requestHandle);
//                        CompletableFuture<UaResponseMessage> f = pending.remove(requestHandle);
//                        if (f != null) f.completeExceptionally(new UaException(StatusCodes.Bad_Timeout, "timeout"));
//                    }, requestTimeout, TimeUnit.MILLISECONDS);
//
//                    timeouts.put(requestHandle, timeout);

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
        long requestHandle = header.getRequestHandle().longValue();

        CompletableFuture<UaResponseMessage> future = pending.remove(requestHandle);

        if (future != null) {
            future.complete(response);
        }

//        Timeout timeout = timeouts.remove(requestHandle);
//        if (timeout != null) timeout.cancel();
    }

    public void receiveServiceFault(ServiceFault serviceFault) {
        ResponseHeader header = serviceFault.getResponseHeader();
        long requestHandle = header.getRequestHandle().longValue();

        CompletableFuture<UaResponseMessage> future = pending.remove(requestHandle);

        if (future != null) {
            StatusCode serviceResult = serviceFault.getResponseHeader().getServiceResult();
            UaException serviceException = new UaException(
                    serviceResult.getValue(), "service fault: " + serviceResult);

            future.completeExceptionally(serviceException);
        }

//        Timeout timeout = timeouts.remove(requestHandle);
//        if (timeout != null) timeout.cancel();
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

            bootstrap.connect(uri.getHost(), uri.getPort()).addListener(f -> {
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

    private static class UaTcpClientHandler extends SimpleChannelInboundHandler<UaResponseMessage> {

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
        UaTcpClientConfig config = UaTcpClientConfig.builder()
                .setEndpointUrl(endpointUrl)
                .build();

        UaTcpStackClient client = new UaTcpStackClient(config);

        GetEndpointsRequest request = new GetEndpointsRequest(
                new RequestHeader(null, DateTime.now(), uint(1), uint(0), null, uint(5000), null),
                endpointUrl, null, new String[]{Stack.UA_TCP_BINARY_TRANSPORT_URI});

        return client.<GetEndpointsResponse>sendRequest(request)
                .thenApply(GetEndpointsResponse::getEndpoints);
    }

}
