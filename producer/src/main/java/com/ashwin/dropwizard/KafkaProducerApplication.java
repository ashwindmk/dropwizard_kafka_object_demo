package com.ashwin.dropwizard;

import com.ashwin.dropwizard.resource.ProducerResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaProducerApplication extends Application<KafkaProducerConfiguration> {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerApplication.class);

    public static void main(String[] args) throws Exception {
        new KafkaProducerApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<KafkaProducerConfiguration> b) {
        logger.debug("kafka-producer: KafkaProducerApplication initialize(" + String.valueOf(b) + ")");
    }

    public void run(KafkaProducerConfiguration kafkaProducerConfiguration, Environment environment) throws Exception {
        logger.info("kafka-producer: Registering RESTful API resources");
        environment.jersey().register(new ProducerResource());
    }
}
