package com.inductiveautomation.opcua.stack.examples.server;

import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.UUID;

import com.inductiveautomation.opcua.stack.core.security.SecurityPolicy;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.enumerated.MessageSecurityMode;
import com.inductiveautomation.opcua.stack.core.types.structured.ResponseHeader;
import com.inductiveautomation.opcua.stack.core.types.structured.TestStackRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.TestStackResponse;
import com.inductiveautomation.opcua.stack.server.tcp.UaTcpServer;
import com.inductiveautomation.opcua.stack.server.tcp.UaTcpServerBuilder;

public class ServerExample {

    private final UaTcpServer server;

    public ServerExample(X509Certificate certificate, KeyPair keyPair) throws Exception {
        server = new UaTcpServerBuilder()
                .setServerName("example")
                .setApplicationName(LocalizedText.english("Stack Example Server"))
                .setApplicationUri(String.format("urn:example-server:%s", UUID.randomUUID()))
                .setCertificate(certificate)
                .setKeyPair(keyPair)
                .build();

        server.addEndpoint("opc.tcp://localhost:12685/example", SecurityPolicy.None, MessageSecurityMode.None);
        server.addEndpoint("opc.tcp://localhost:12685/example", SecurityPolicy.Basic128Rsa15, MessageSecurityMode.SignAndEncrypt);

        server.addRequestHandler(TestStackRequest.class, service -> {
            TestStackRequest request = service.getRequest();

            ResponseHeader header = service.createResponseHeader();

            service.setResponse(new TestStackResponse(header, request.getInput()));
        });
    }

    public void startup() {
        server.startup();
    }

    public void shutdown() {
        server.shutdown();
    }

}
