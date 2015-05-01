package com.digitalpetri.opcua.stack;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.beust.jcommander.internal.Lists;
import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.client.UaTcpStackClientConfig;
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
import com.digitalpetri.opcua.stack.server.tcp.UaTcpServerBuilder;
import com.digitalpetri.opcua.stack.server.tcp.UaTcpStackServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.ubyte;
import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;
import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.ulong;
import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.ushort;
import static org.testng.Assert.assertEquals;

public class ClientServerTest extends SecurityFixture {

    @DataProvider
    public Object[][] getVariants() {
        return new Object[][]{
                {new Variant(true)},
                {new Variant((byte) 1)},
                {new Variant(ubyte(1))},
                {new Variant((short) 1)},
                {new Variant(ushort(1))},
                {new Variant(1)},
                {new Variant(uint(1))},
                {new Variant(1L)},
                {new Variant(ulong(1L))},
                {new Variant(3.14f)},
                {new Variant(6.12d)},
                {new Variant("hello, world")},
                {new Variant(DateTime.now())},
                {new Variant(UUID.randomUUID())},
                {new Variant(ByteString.of(new byte[]{1, 2, 3, 4}))},
                {new Variant(new XmlElement("<tag>hello</tag>"))},
                {new Variant(new NodeId(0, 42))},
                {new Variant(new ExpandedNodeId(1, 42, "uri", 1))},
                {new Variant(StatusCode.GOOD)},
                {new Variant(new QualifiedName(0, "QualifiedName"))},
                {new Variant(LocalizedText.english("LocalizedText"))},
                {new Variant(new ExtensionObject(new ReadValueId(NodeId.NULL_VALUE, uint(1), null, new QualifiedName(0, "DataEncoding"))))},
        };
    }

    private Logger logger = LoggerFactory.getLogger(getClass());

    private EndpointDescription[] endpoints;

    UaTcpStackServer server;

    @BeforeTest
    public void setUpClientServer() throws Exception {
        super.setUp();

        CryptoRestrictions.remove();
        // ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.PARANOID);

        server = new UaTcpServerBuilder()
                .setServerName("test")
                .setCertificateManager(serverCertificateManager)
                .build();

        server.addEndpoint("opc.tcp://localhost:12685/test", null)
                .addEndpoint("opc.tcp://localhost:12685/test", null, serverCertificate, SecurityPolicy.Basic128Rsa15, MessageSecurityMode.Sign)
                .addEndpoint("opc.tcp://localhost:12685/test", null, serverCertificate, SecurityPolicy.Basic256, MessageSecurityMode.Sign)
                .addEndpoint("opc.tcp://localhost:12685/test", null, serverCertificate, SecurityPolicy.Basic256Sha256, MessageSecurityMode.Sign)
                .addEndpoint("opc.tcp://localhost:12685/test", null, serverCertificate, SecurityPolicy.Basic128Rsa15, MessageSecurityMode.SignAndEncrypt)
                .addEndpoint("opc.tcp://localhost:12685/test", null, serverCertificate, SecurityPolicy.Basic256, MessageSecurityMode.SignAndEncrypt)
                .addEndpoint("opc.tcp://localhost:12685/test", null, serverCertificate, SecurityPolicy.Basic256Sha256, MessageSecurityMode.SignAndEncrypt);

        server.addRequestHandler(TestStackRequest.class, (service) -> {
            TestStackRequest request = service.getRequest();

            ResponseHeader header = new ResponseHeader(
                    DateTime.now(),
                    request.getRequestHeader().getRequestHandle(),
                    StatusCode.GOOD,
                    null, null, null
            );

            service.setResponse(new TestStackResponse(header, request.getInput()));
        });

        server.startup();

        endpoints = UaTcpStackClient.getEndpoints("opc.tcp://localhost:12685/test").get();
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

        UaTcpStackClient client = createClient(endpoint);

        connectAndTest(input, client);
    }


    @Test(dataProvider = "getVariants")
    public void testClientServerRoundTrip_TestStack_Basic128Rsa15_Sign(Variant input) throws Exception {
        EndpointDescription endpoint = endpoints[1];

        logger.info("SecurityPolicy={}, MessageSecurityMode={}, input={}",
                SecurityPolicy.fromUri(endpoint.getSecurityPolicyUri()), endpoint.getSecurityMode(), input);

        UaTcpStackClient client = createClient(endpoint);

        connectAndTest(input, client);
    }

    @Test(dataProvider = "getVariants")
    public void testClientServerRoundTrip_TestStack_Basic256_Sign(Variant input) throws Exception {
        EndpointDescription endpoint = endpoints[2];

        logger.info("SecurityPolicy={}, MessageSecurityMode={}, input={}",
                SecurityPolicy.fromUri(endpoint.getSecurityPolicyUri()), endpoint.getSecurityMode(), input);

        UaTcpStackClient client = createClient(endpoint);

        connectAndTest(input, client);
    }

    @Test(dataProvider = "getVariants")
    public void testClientServerRoundTrip_TestStack_Basic256Sha256_Sign(Variant input) throws Exception {
        EndpointDescription endpoint = endpoints[3];

        logger.info("SecurityPolicy={}, MessageSecurityMode={}, input={}",
                SecurityPolicy.fromUri(endpoint.getSecurityPolicyUri()), endpoint.getSecurityMode(), input);

        UaTcpStackClient client = createClient(endpoint);

        connectAndTest(input, client);
    }

    @Test(dataProvider = "getVariants")
    public void testClientServerRoundTrip_TestStack_Basic128Rsa15_SignAndEncrypt(Variant input) throws Exception {
        EndpointDescription endpoint = endpoints[4];

        logger.info("SecurityPolicy={}, MessageSecurityMode={}, input={}",
                SecurityPolicy.fromUri(endpoint.getSecurityPolicyUri()), endpoint.getSecurityMode(), input);

        UaTcpStackClient client = createClient(endpoint);

        connectAndTest(input, client);
    }

    @Test(dataProvider = "getVariants")
    public void testClientServerRoundTrip_TestStack_Basic256_SignAndEncrypt(Variant input) throws Exception {
        EndpointDescription endpoint = endpoints[5];

        logger.info("SecurityPolicy={}, MessageSecurityMode={}, input={}",
                SecurityPolicy.fromUri(endpoint.getSecurityPolicyUri()), endpoint.getSecurityMode(), input);

        UaTcpStackClient client = createClient(endpoint);

        connectAndTest(input, client);
    }

    @Test(dataProvider = "getVariants")
    public void testClientServerRoundTrip_TestStack_Basic256Sha256_SignAndEncrypt(Variant input) throws Exception {
        EndpointDescription endpoint = endpoints[6];

        logger.info("SecurityPolicy={}, MessageSecurityMode={}, input={}",
                SecurityPolicy.fromUri(endpoint.getSecurityPolicyUri()), endpoint.getSecurityMode(), input);

        UaTcpStackClient client = createClient(endpoint);

        connectAndTest(input, client);
    }

    @Test
    public void testClientStateMachine() throws Exception {
        EndpointDescription endpoint = endpoints[0];
        Variant input = new Variant(42);

        logger.info("SecurityPolicy={}, MessageSecurityMode={}, input={}",
                SecurityPolicy.fromUri(endpoint.getSecurityPolicyUri()), endpoint.getSecurityMode(), input);

        UaTcpStackClient client = createClient(endpoint);

        // Test some where we don't wait for disconnect to finish...
        for (int i = 0; i < 10; i++) {
            RequestHeader header = new RequestHeader(
                    NodeId.NULL_VALUE,
                    DateTime.now(),
                    uint(i), uint(0), null, uint(60), null);

            TestStackRequest request = new TestStackRequest(header, uint(i), i, input);

            UaResponseMessage response = client.sendRequest(request).get();

            logger.info("get response: {}", response);

            client.disconnect();
        }

        // and test some where we DO wait...
        for (int i = 0; i < 10; i++) {
            RequestHeader header = new RequestHeader(
                    NodeId.NULL_VALUE,
                    DateTime.now(),
                    uint(i), uint(0), null, uint(60), null);

            TestStackRequest request = new TestStackRequest(header, uint(i), i, input);

            logger.info("sending request: {}", request);
            UaResponseMessage response = client.sendRequest(request).get();
            logger.info("get response: {}", response);

            client.disconnect();
            Thread.sleep(100);
        }
    }

    private UaTcpStackClient createClient(EndpointDescription endpoint) throws UaException {
        UaTcpStackClientConfig config = UaTcpStackClientConfig.builder()
                .setEndpoint(endpoint)
                .setKeyPair(clientKeyPair)
                .setCertificate(clientCertificate)
                .build();

        return new UaTcpStackClient(config);
    }

    private void connectAndTest(Variant input, UaTcpStackClient client) throws InterruptedException, java.util.concurrent.ExecutionException {
        client.connect().get();

        List<TestStackRequest> requests = Lists.newArrayList();
        List<CompletableFuture<? extends UaResponseMessage>> futures = Lists.newArrayList();

        for (int i = 0; i < 100; i++) {
            RequestHeader header = new RequestHeader(
                    NodeId.NULL_VALUE,
                    DateTime.now(),
                    uint(i), uint(0), null, uint(60), null
            );

            requests.add(new TestStackRequest(header, uint(i), i, input));

            CompletableFuture<TestStackResponse> future = new CompletableFuture<>();

            future.thenAccept((response) -> assertEquals(response.getOutput(), input));

            futures.add(future);
        }

        client.sendRequests(requests, futures);

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).get();
    }

}
