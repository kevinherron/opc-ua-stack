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
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class UaTcpStackClient implements UaStackClient {

    private static final long DEFAULT_TIMEOUT_MS = 60000;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<UInteger, CompletableFuture<UaResponseMessage>> pending = Maps.newConcurrentMap();
    private final Map<UInteger, Timeout> timeouts = Maps.newConcurrentMap();
    private final HashedWheelTimer wheelTimer = Stack.sharedWheelTimer();

    private volatile ClientSecureChannel secureChannel;

    private final ApplicationDescription application;

    private final ChannelManager channelManager;

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

        channelManager = new ChannelManager(this);
    }

    @Override
    public CompletableFuture<UaStackClient> connect() {
        CompletableFuture<UaStackClient> future = new CompletableFuture<>();

        channelManager.getChannel().whenComplete((ch, ex) -> {
            if (ch != null) future.complete(this);
            else future.completeExceptionally(ex);
        });

        return future;
    }

    @Override
    public CompletableFuture<UaStackClient> disconnect() {
        channelManager.disconnect();

        return CompletableFuture.completedFuture(this);
    }

    @SuppressWarnings("unchecked")
    public <T extends UaResponseMessage> CompletableFuture<T> sendRequest(UaRequestMessage request) {
        return channelManager.getChannel().thenCompose(ch -> {
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

            logger.info("wrote request: " + request);

            return future;
        });
    }

    @SuppressWarnings("unchecked")
    public void sendRequests(List<? extends UaRequestMessage> requests,
                             List<CompletableFuture<? extends UaResponseMessage>> futures) {

        Preconditions.checkArgument(requests.size() == futures.size(),
                "requests and futures parameters must be same size");

        channelManager.getChannel().whenComplete((ch, ex) -> {
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

    public CompletableFuture<Channel> getChannelFuture() {
        return channelManager.getChannel();
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

    public void receiveResponse(UaResponseMessage response) {
        logger.info("read response:" + response.getClass().getSimpleName());

        ResponseHeader header = response.getResponseHeader();
        UInteger requestHandle = header.getRequestHandle();

        CompletableFuture<UaResponseMessage> future = pending.remove(requestHandle);

        if (future != null) {
            if (header.getServiceResult().isGood()) {
                future.complete(response);
            } else {
                ServiceFault serviceFault;

                if (response instanceof ServiceFault) {
                    serviceFault = (ServiceFault) response;
                } else {
                    serviceFault = new ServiceFault(header);
                }

                future.completeExceptionally(new UaServiceFaultException(serviceFault));
            }

            Timeout timeout = timeouts.remove(requestHandle);
            if (timeout != null) timeout.cancel();
        } else {
            logger.debug("Received {} for unknown requestHandle: {}",
                    response.getClass().getSimpleName(), requestHandle);
        }
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
