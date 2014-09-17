package com.digitalpetri.opcua.stack;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.beust.jcommander.internal.Lists;
import com.digitalpetri.opcua.stack.client.UaTcpClient;
import com.digitalpetri.opcua.stack.client.UaTcpClientBuilder;
import com.digitalpetri.opcua.stack.core.Stack;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.ExtensionObject;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.OverloadedType;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.XmlElement;
import com.digitalpetri.opcua.stack.core.types.enumerated.MessageSecurityMode;
import com.digitalpetri.opcua.stack.core.types.structured.EndpointDescription;
import com.digitalpetri.opcua.stack.core.types.structured.ReadValueId;
import com.digitalpetri.opcua.stack.core.types.structured.RequestHeader;
import com.digitalpetri.opcua.stack.core.types.structured.ResponseHeader;
import com.digitalpetri.opcua.stack.core.types.structured.TestStackRequest;
import com.digitalpetri.opcua.stack.core.types.structured.TestStackResponse;
import com.digitalpetri.opcua.stack.core.util.CryptoRestrictions;
import com.digitalpetri.opcua.stack.server.tcp.SocketServer;
import com.digitalpetri.opcua.stack.server.tcp.UaTcpServer;
import com.digitalpetri.opcua.stack.server.tcp.UaTcpServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ClientServerTest extends SecurityFixture {

    @DataProvider
    public Object[][] getVariants() {
        return new Object[][]{
                {new Variant(true)},
                {new Variant((byte) 1)},
                {new Variant((short) 1, OverloadedType.UByte)},
                {new Variant((short) 1)},
                {new Variant(1, OverloadedType.UInt16)},
                {new Variant(1)},
                {new Variant(1L, OverloadedType.UInt32)},
                {new Variant(1L)},
                {new Variant(1L, OverloadedType.UInt64)},
                {new Variant(3.14f)},
                {new Variant(6.12d)},
                {new Variant("hello, world")},
                {new Variant(DateTime.now())},
                {new Variant(UUID.randomUUID())},
                {new Variant(ByteString.of(new byte[]{1, 2, 3, 4}))},
                {new Variant(new XmlElement("<tag>hello</tag>"))},
                {new Variant(new NodeId(0, 42))},
                {new Variant(new ExpandedNodeId(1, 42, "uri", 1))},
                {new Variant(StatusCode.Good)},
                {new Variant(new QualifiedName(0, "QualifiedName"))},
                {new Variant(LocalizedText.english("LocalizedText"))},
                {new Variant(new ExtensionObject(new ReadValueId(NodeId.NullValue, 1L, null, new QualifiedName(0, "DataEncoding"))))},
        };
    }

    private Logger logger = LoggerFactory.getLogger(getClass());

    private EndpointDescription[] endpoints;

    UaTcpServer server;

    @BeforeTest
    public void setUpClientServer() throws Exception {
        super.setUp();

        CryptoRestrictions.remove();
        // ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.PARANOID);

        server = new UaTcpServerBuilder()
                .setCertificate(serverCertificate)
                .setKeyPair(serverKeyPair)
                .build();

        server.addEndpoint("opc.tcp://localhost:12685/test", SecurityPolicy.None, MessageSecurityMode.None)
                .addEndpoint("opc.tcp://localhost:12685/test", SecurityPolicy.Basic128Rsa15, MessageSecurityMode.Sign)
                .addEndpoint("opc.tcp://localhost:12685/test", SecurityPolicy.Basic256, MessageSecurityMode.Sign)
                .addEndpoint("opc.tcp://localhost:12685/test", SecurityPolicy.Basic256Sha256, MessageSecurityMode.Sign)
                .addEndpoint("opc.tcp://localhost:12685/test", SecurityPolicy.Basic128Rsa15, MessageSecurityMode.SignAndEncrypt)
                .addEndpoint("opc.tcp://localhost:12685/test", SecurityPolicy.Basic256, MessageSecurityMode.SignAndEncrypt)
                .addEndpoint("opc.tcp://localhost:12685/test", SecurityPolicy.Basic256Sha256, MessageSecurityMode.SignAndEncrypt);

        server.addRequestHandler(TestStackRequest.class, (service) -> {
            TestStackRequest request = service.getRequest();

            ResponseHeader header = new ResponseHeader(
                    DateTime.now(),
                    request.getRequestHeader().getRequestHandle(),
                    StatusCode.Good,
                    null, null, null
            );

            service.setResponse(new TestStackResponse(header, request.getInput()));
        });

        server.startup();

        endpoints = UaTcpClient.getEndpoints("opc.tcp://localhost:12685/test").get();
    }

    @AfterTest
    public void tearDownClientServer() throws Exception {
        SocketServer.shutdownAll();
        Stack.sharedEventLoop().shutdownGracefully();
    }

    @Test(dataProvider = "getVariants")
    public void testClientServerRoundTrip_TestStack_NoSecurity(Variant input) throws Exception {
        EndpointDescription endpoint = endpoints[0];

        logger.info("SecurityPolicy={}, MessageSecurityMode={}, input={}",
                SecurityPolicy.fromUri(endpoint.getSecurityPolicyUri()), endpoint.getSecurityMode(), input);

        UaTcpClient client = createClient(endpoint);

        connectAndTest(input, client);
    }


    @Test(dataProvider = "getVariants")
    public void testClientServerRoundTrip_TestStack_Basic128Rsa15_Sign(Variant input) throws Exception {
        EndpointDescription endpoint = endpoints[1];

        logger.info("SecurityPolicy={}, MessageSecurityMode={}, input={}",
                SecurityPolicy.fromUri(endpoint.getSecurityPolicyUri()), endpoint.getSecurityMode(), input);

        UaTcpClient client = createClient(endpoint);

        connectAndTest(input, client);
    }

    @Test(dataProvider = "getVariants")
    public void testClientServerRoundTrip_TestStack_Basic256_Sign(Variant input) throws Exception {
        EndpointDescription endpoint = endpoints[2];

        logger.info("SecurityPolicy={}, MessageSecurityMode={}, input={}",
                SecurityPolicy.fromUri(endpoint.getSecurityPolicyUri()), endpoint.getSecurityMode(), input);

        UaTcpClient client = createClient(endpoint);

        connectAndTest(input, client);
    }

    @Test(dataProvider = "getVariants")
    public void testClientServerRoundTrip_TestStack_Basic256Sha256_Sign(Variant input) throws Exception {
        EndpointDescription endpoint = endpoints[3];

        logger.info("SecurityPolicy={}, MessageSecurityMode={}, input={}",
                SecurityPolicy.fromUri(endpoint.getSecurityPolicyUri()), endpoint.getSecurityMode(), input);

        UaTcpClient client = createClient(endpoint);

        connectAndTest(input, client);
    }

    @Test(dataProvider = "getVariants")
    public void testClientServerRoundTrip_TestStack_Basic128Rsa15_SignAndEncrypt(Variant input) throws Exception {
        EndpointDescription endpoint = endpoints[4];

        logger.info("SecurityPolicy={}, MessageSecurityMode={}, input={}",
                SecurityPolicy.fromUri(endpoint.getSecurityPolicyUri()), endpoint.getSecurityMode(), input);

        UaTcpClient client = createClient(endpoint);

        connectAndTest(input, client);
    }

    @Test(dataProvider = "getVariants")
    public void testClientServerRoundTrip_TestStack_Basic256_SignAndEncrypt(Variant input) throws Exception {
        EndpointDescription endpoint = endpoints[5];

        logger.info("SecurityPolicy={}, MessageSecurityMode={}, input={}",
                SecurityPolicy.fromUri(endpoint.getSecurityPolicyUri()), endpoint.getSecurityMode(), input);

        UaTcpClient client = createClient(endpoint);

        connectAndTest(input, client);
    }

    @Test(dataProvider = "getVariants")
    public void testClientServerRoundTrip_TestStack_Basic256Sha256_SignAndEncrypt(Variant input) throws Exception {
        EndpointDescription endpoint = endpoints[6];

        logger.info("SecurityPolicy={}, MessageSecurityMode={}, input={}",
                SecurityPolicy.fromUri(endpoint.getSecurityPolicyUri()), endpoint.getSecurityMode(), input);

        UaTcpClient client = createClient(endpoint);

        connectAndTest(input, client);
    }

    private UaTcpClient createClient(EndpointDescription endpoint) throws UaException {
        return new UaTcpClientBuilder()
                .setKeyPair(clientKeyPair)
                .setCertificate(clientCertificate)
                .build(endpoint);
    }

    private void connectAndTest(Variant input, UaTcpClient client) throws InterruptedException, java.util.concurrent.ExecutionException {
        client.connect().get();

        List<TestStackRequest> requests = Lists.newArrayList();
        List<CompletableFuture<? extends UaResponseMessage>> futures = Lists.newArrayList();

        for (int i = 0; i < 100; i++) {
            RequestHeader header = new RequestHeader(
                    NodeId.NullValue,
                    DateTime.now(),
                    (long) i, 0L, null, 60L, null
            );

            requests.add(new TestStackRequest(header, (long) i, i, input));

            CompletableFuture<TestStackResponse> future = new CompletableFuture<>();

            future.thenAccept((response) -> assertEquals(response.getOutput(), input));

            futures.add(future);
        }

        client.sendRequests(requests, futures);

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).get();
    }

}
