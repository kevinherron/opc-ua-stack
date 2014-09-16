package com.digitalpetri.opcua.stack.examples.server;

import java.net.InetAddress;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Optional;
import java.util.UUID;

import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.enumerated.MessageSecurityMode;
import com.digitalpetri.opcua.stack.core.types.structured.ResponseHeader;
import com.digitalpetri.opcua.stack.core.types.structured.TestStackRequest;
import com.digitalpetri.opcua.stack.core.types.structured.TestStackResponse;
import com.digitalpetri.opcua.stack.server.tcp.UaTcpServer;
import com.digitalpetri.opcua.stack.server.tcp.UaTcpServerBuilder;

public class ServerExample {

    private static final String SERVER_ALIAS = "server-test-certificate";
    private static final char[] PASSWORD = "test".toCharArray();

    private final UaTcpServer server;

    public ServerExample() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(getClass().getClassLoader().getResourceAsStream("example-keystore.pfx"), PASSWORD);

        Certificate certificate = null;
        KeyPair keyPair = null;

        Key serverPrivateKey = keyStore.getKey(SERVER_ALIAS, PASSWORD);
        if (serverPrivateKey instanceof PrivateKey) {
            certificate = keyStore.getCertificate(SERVER_ALIAS);
            PublicKey serverPublicKey = certificate.getPublicKey();
            keyPair = new KeyPair(serverPublicKey, (PrivateKey) serverPrivateKey);
        }

        server = new UaTcpServerBuilder()
                .setApplicationName(LocalizedText.english("Stack Example Server"))
                .setApplicationUri(String.format("urn:%s:example-server:%s", getHostname(), UUID.randomUUID()))
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

    private static String getHostname() {
        return Optional.ofNullable(System.getProperty("hostname")).orElseGet(() -> {
            try {
                return InetAddress.getLocalHost().getCanonicalHostName();
            } catch (Throwable ignored) {
                return "localhost";
            }
        });
    }

}
