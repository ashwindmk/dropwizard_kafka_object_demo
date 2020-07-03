package com.ashwin.dropwizard;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KafkaMessageConsumer {
    Logger logger = LoggerFactory.getLogger(KafkaMessageConsumer.class);

    private ConsumerConnector consumerConnector;
    private KafkaConfiguration kafkaConfiguration;
    private ExecutorService executor;
    private List<KafkaMessageHandler> messageHandlers;

    public KafkaMessageConsumer(ConsumerConnector consumerConnector, KafkaConfiguration kafkaConfiguration, List<KafkaMessageHandler> messageHandlers) {
        this.consumerConnector = consumerConnector;
        this.kafkaConfiguration = kafkaConfiguration;
        this.messageHandlers = messageHandlers;
        logger.info("New Kafka ConsumerConnector {} is created with the {}", consumerConnector, kafkaConfiguration);
    }

    public void start() {
        int threadCount = messageHandlers.size();
        String topic = kafkaConfiguration.getTopic();

        Map<String, Integer> topicCount = new HashMap<String, Integer>();
        topicCount.put(topic, new Integer(threadCount));

        Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreams = consumerConnector.createMessageStreams(topicCount);
        List<KafkaStream<byte[], byte[]>> streams = consumerStreams.get(topic);

        executor = Executors.newFixedThreadPool(threadCount);

        int threadNumber = 0;
        for (final KafkaStream stream : streams) {
            executor.submit(new KafkaMessageRunnable(stream, threadNumber, messageHandlers.get(threadNumber)));
            threadNumber++;
            logger.info("New thread has been created for the consumerConnector " + consumerConnector + " to the topic " + topic);
        }
        logger.info("New consumerConnector " + consumerConnector + " for topic " + topic + " is running.");
    }

    public void stop() {
        if (consumerConnector != null) {
            consumerConnector.shutdown();
            logger.info("ConsumerConnector {} has been shut down", consumerConnector );
        }
        if (executor != null) {
            executor.shutdown();
        }
    }

    class KafkaMessageRunnable implements Runnable {
        private KafkaStream stream;
        private int threadNumber;
        private KafkaMessageHandler messageHandler;

        KafkaMessageRunnable(KafkaStream stream, int threadNumber, KafkaMessageHandler messageHandler) {
            this.threadNumber = threadNumber;
            this.stream = stream;
            this.messageHandler = messageHandler;
        }

        @Override
        public void run() {
            ConsumerIterator<byte[], byte[]> it = stream.iterator();
            while (it.hasNext()) {
                MessageAndMetadata<byte[], byte[]> kafkaBlock = it.next();
                byte[] msg = kafkaBlock.message();
                logger.debug("Consuming message: " + msg);
                messageHandler.dispose(msg);
            }
        }
    }
}
