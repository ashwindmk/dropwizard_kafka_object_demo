package com.ashwin.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.javaapi.consumer.ConsumerConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class KafkaConsumerApplication extends Application<KafkaConsumerConfiguration> {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerApplication.class);

    public static void main(String[] args) throws Exception {
        new KafkaConsumerApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<KafkaConsumerConfiguration> b) {
    }

    @Override
    public void run(KafkaConsumerConfiguration config, Environment env) throws Exception {
        List<KafkaMessageHandler> messageHandlers= new ArrayList<KafkaMessageHandler>(config.getMessageHandlerConfig().getNumber());
        for (int i = 0; i < config.getMessageHandlerConfig().getNumber(); i++) {
            messageHandlers.add(new KafkaMessageHandler());
        }

        KafkaConfiguration kafkaConfig = config.getKafkaConfig();

        Properties props = new Properties();
        props.put("zookeeper.connect", kafkaConfig.getZookeeperConnect());
        props.put("group.id", kafkaConfig.getGroupId());
        props.put("zookeeper.session.timeout.ms", Integer.toString(kafkaConfig.getZookeeperSessionTimeout()));
        props.put("zookeeper.sync.time.ms", Integer.toString(kafkaConfig.getZookeeperSyncTime()));
        props.put("auto.commit.interval.ms", Integer.toString(kafkaConfig.getAutoCommitInterval()));

        ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
        KafkaMessageConsumer consumer = new KafkaMessageConsumer(consumerConnector, kafkaConfig, messageHandlers);

        KafkaMessageConsumerManager kafkaMessageConsumerManager = new KafkaMessageConsumerManager(consumer);
        env.lifecycle().manage(kafkaMessageConsumerManager);

        logger.info("New KafkaMessageConsumerManager with a KafkaMessageConsumer has been created and managed by the dropwizard environment.");
    }
}
