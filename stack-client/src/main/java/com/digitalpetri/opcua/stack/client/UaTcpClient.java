package com.digitalpetri.opcua.stack.client;

import java.security.KeyPair;
import java.security.cert.Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import com.digitalpetri.opcua.stack.core.channel.ClientSecureChannel;
import com.digitalpetri.opcua.stack.core.Stack;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.channel.ChannelConfig;
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
import io.netty.channel.Channel;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UaTcpClient {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ClientSecureChannel secureChannel;
    private final HashedWheelTimer wheelTimer = Stack.WHEEL_TIMER;

    private final Map<Long, CompletableFuture<UaResponseMessage>> pending = Maps.newConcurrentMap();
    private final Map<Long, Timeout> timeouts = Maps.newConcurrentMap();

    private final ClientChannelManager channelManager;

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

        channelManager = new ClientChannelManager(this);

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

        channelManager = new ClientChannelManager(this);
    }

    public CompletableFuture<Channel> connect() {
        return channelManager.getChannel();
    }

    public CompletableFuture<Void> disconnect() {
        return channelManager.disconnect();
    }

    @SuppressWarnings("unchecked")
    public <T extends UaResponseMessage> CompletableFuture<T> sendRequest(UaRequestMessage request) {
        CompletableFuture<T> future = new CompletableFuture<>();

        channelManager.getChannel().whenComplete((ch, ex) -> {
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

        channelManager.getChannel().whenComplete((ch, ex) -> {
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

    public void receiveResponse(UaResponseMessage response) {
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

    public ChannelConfig getChannelConfig() {
        return channelConfig;
    }

    public ClientSecureChannel getSecureChannel() {
        return secureChannel;
    }

    public ApplicationDescription getApplication() {
        return application;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public long getRequestTimeout() {
        return requestTimeout;
    }

    public ExecutorService getExecutor() {
        return executor;
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
