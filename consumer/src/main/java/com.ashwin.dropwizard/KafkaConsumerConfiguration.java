package com.ashwin.dropwizard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public class KafkaConsumerConfiguration extends Configuration {
    @JsonProperty
    public String appName;

    @JsonProperty
    @NotNull
    private KafkaConfiguration kafkaConfig = new KafkaConfiguration();

    @JsonProperty
    @NotNull
    private MessageHandlerConfiguration messageHandlerConfig = new MessageHandlerConfiguration();

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public MessageHandlerConfiguration getMessageHandlerConfig() {
        return messageHandlerConfig;
    }

    public KafkaConfiguration getKafkaConfig() {
        return kafkaConfig;
    }
}
