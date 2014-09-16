package com.digitalpetri.opcua.stack.examples;

import com.digitalpetri.opcua.stack.core.types.structured.TestStackResponse;
import com.digitalpetri.opcua.stack.examples.client.ClientExample;
import com.digitalpetri.opcua.stack.examples.server.ServerExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientServerExample {

    public static void main(String[] args) throws Exception {
        new ClientServerExample();
    }

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ClientServerExample() throws Exception {
        ServerExample server = new ServerExample();
        server.startup();

        ClientExample client = new ClientExample();

        logger.info("=== Sending and receiving synchronously ===");

        for (int i = 0; i < 5; i++) {
            logger.info("Sending TestStackRequest input={}", i);
            TestStackResponse response = client.testStack(i).get();
            logger.info("Received TestStackResponse output={}", response.getOutput());
        }

        logger.info("=== Sending and receiving asynchronously ===");

        for (int i = 0; i < 5; i++) {
            logger.info("Sending TestStackRequest input={}", i);

            client.testStack(i).whenComplete((response, ex) -> {
                if (response != null) {
                    logger.info("Received TestStackResponse output={}", response.getOutput());
                } else {
                    logger.error("Error: {}", ex.getMessage(), ex);
                }
            });
        }
    }

}
