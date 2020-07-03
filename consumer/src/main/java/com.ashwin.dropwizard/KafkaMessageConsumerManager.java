package com.ashwin.dropwizard;

import io.dropwizard.lifecycle.Managed;

public class KafkaMessageConsumerManager implements Managed {
    private KafkaMessageConsumer kafkaMessageConsumer;

    public KafkaMessageConsumerManager(KafkaMessageConsumer kafkaMessageConsumer) {
        this.kafkaMessageConsumer = kafkaMessageConsumer;
    }

    @Override
    public void start() throws Exception {
        kafkaMessageConsumer.start();
    }

    @Override
    public void stop() throws Exception {
        kafkaMessageConsumer.stop();
    }
}
