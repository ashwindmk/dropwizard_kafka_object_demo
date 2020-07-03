package com.ashwin.dropwizard.repository;

import com.ashwin.dropwizard.model.User;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerRepository {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    public String sendMessage(String topicName, User user) {
        logger.debug("Entering KafkaProducerService.sendMessage method (" + topicName + ", " + user + ")");
        String status;
        try {
            final Producer<byte[], byte[]> producer;
            final Properties props = new Properties();
            props.put("bootstrap.servers", "localhost:9092");
            props.put("acks", "all");
            props.put("retries", 0);
            props.put("batch.size", 16384);
            props.put("linger.ms", 1);
            props.put("buffer.memory", 33554432);
            props.put("key.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
            producer = new KafkaProducer<>(props);
            producer.send(new ProducerRecord<>(topicName, user.toJson().getBytes()));
            producer.close();
            status = "success";
            logger.info("status is: {}", status);
            logger.info("Leaving KafkaProducerService.sendMessage method");
        } catch (Exception e) {
            logger.error("Exception: Error sending data to topic ", topicName, e.getMessage());
            status = "error";
            logger.error("status is: {}", status);
            return status;
        }
        return status;
    }
}
