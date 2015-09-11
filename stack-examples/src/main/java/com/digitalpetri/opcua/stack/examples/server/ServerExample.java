package com.digitalpetri.opcua.stack.examples.server;

import java.io.File;
import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.UUID;

import com.digitalpetri.opcua.stack.core.application.CertificateManager;
import com.digitalpetri.opcua.stack.core.application.CertificateValidator;
import com.digitalpetri.opcua.stack.core.application.DefaultCertificateManager;
import com.digitalpetri.opcua.stack.core.application.DefaultCertificateValidator;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.enumerated.MessageSecurityMode;
import com.digitalpetri.opcua.stack.core.types.structured.ResponseHeader;
import com.digitalpetri.opcua.stack.core.types.structured.TestStackRequest;
import com.digitalpetri.opcua.stack.core.types.structured.TestStackResponse;
import com.digitalpetri.opcua.stack.server.config.UaTcpStackServerConfig;
import com.digitalpetri.opcua.stack.server.tcp.UaTcpStackServer;

public class ServerExample {

    private final UaTcpStackServer server;

    public ServerExample(X509Certificate certificate, KeyPair keyPair) throws Exception {
        File securityDir = new File("./security/");

        if (!securityDir.exists() && !securityDir.mkdirs()) {
            throw new Exception("unable to create security directory");
        }

        CertificateManager certificateManager = new DefaultCertificateManager(keyPair, certificate);
        CertificateValidator certificateValidator = new DefaultCertificateValidator(securityDir);

        UaTcpStackServerConfig config = UaTcpStackServerConfig.builder()
                .setServerName("example")
                .setApplicationName(LocalizedText.english("Stack Example Server"))
                .setApplicationUri(String.format("urn:example-server:%s", UUID.randomUUID()))
                .setCertificateManager(certificateManager)
                .setCertificateValidator(certificateValidator)
                .build();

        server = new UaTcpStackServer(config);

        server.addEndpoint("opc.tcp://localhost:12685/example", null, certificate, SecurityPolicy.None, MessageSecurityMode.None);
        server.addEndpoint("opc.tcp://localhost:12685/example", null, certificate, SecurityPolicy.Basic128Rsa15, MessageSecurityMode.SignAndEncrypt);

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
