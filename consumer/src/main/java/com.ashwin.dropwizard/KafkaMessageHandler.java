package com.ashwin.dropwizard;

import com.ashwin.dropwizard.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class KafkaMessageHandler {
    Logger logger = LoggerFactory.getLogger(KafkaMessageHandler.class);

    /*public void start() {
        logger.info("Kafka Message Handler started");
    }

    public void stop() {
        logger.info("Kafka Message Handler stopped");
    }*/

    public void dispose(byte[] message) {
        String value = new String(message, StandardCharsets.UTF_8);
        User user = User.fromJson(value);
        logger.info("Kafka Message Handler diposed: " + user);
    }
}
