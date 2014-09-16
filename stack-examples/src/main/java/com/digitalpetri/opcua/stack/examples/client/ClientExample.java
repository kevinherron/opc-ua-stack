package com.digitalpetri.opcua.stack.examples.client;

import java.net.InetAddress;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

import com.digitalpetri.opcua.stack.client.UaTcpClient;
import com.digitalpetri.opcua.stack.client.UaTcpClientBuilder;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.structured.EndpointDescription;
import com.digitalpetri.opcua.stack.core.types.structured.RequestHeader;
import com.digitalpetri.opcua.stack.core.types.structured.TestStackRequest;
import com.digitalpetri.opcua.stack.core.types.structured.TestStackResponse;

public class ClientExample {

    private static final String CLIENT_ALIAS = "client-test-certificate";
    private static final char[] PASSWORD = "test".toCharArray();

    private final AtomicLong requestHandle = new AtomicLong(1L);

    private final UaTcpClient client;

    public ClientExample() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(getClass().getClassLoader().getResourceAsStream("example-keystore.pfx"), PASSWORD);

        Certificate certificate = null;
        KeyPair keyPair = null;

        Key clientPrivateKey = keyStore.getKey(CLIENT_ALIAS, PASSWORD);
        if (clientPrivateKey instanceof PrivateKey) {
            certificate = keyStore.getCertificate(CLIENT_ALIAS);
            PublicKey clientPublicKey = certificate.getPublicKey();
            keyPair = new KeyPair(clientPublicKey, (PrivateKey) clientPrivateKey);
        }

        // Query endpoints and select highest security level.
        EndpointDescription[] endpoints = UaTcpClient.getEndpoints("opc.tcp://localhost:12685/example").get();

        EndpointDescription endpoint = Arrays.stream(endpoints)
                .sorted((e1, e2) -> e2.getSecurityLevel() - e1.getSecurityLevel())
                .findFirst()
                .orElseThrow(() -> new Exception("no endpoints returned"));

        client = new UaTcpClientBuilder()
                .setApplicationName(LocalizedText.english("Stack Example Client"))
                .setApplicationUri(String.format("urn:%s:example-client:%s", getHostname(), UUID.randomUUID()))
                .setCertificate(certificate)
                .setKeyPair(keyPair)
                .build(endpoint);
    }

    public CompletableFuture<TestStackResponse> testStack(int input) {
        RequestHeader header = new RequestHeader(
                NodeId.NullValue,
                DateTime.now(),
                requestHandle.getAndIncrement(),
                0L, null, 60L, null
        );

        TestStackRequest request = new TestStackRequest(header, 0L, 1, new Variant(input));

        return client.sendRequest(request);
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
