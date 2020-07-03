package com.ashwin.dropwizard;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageHandlerConfiguration {
    @JsonProperty
    private int number = 1;

    public MessageHandlerConfiguration() {
    }

    public MessageHandlerConfiguration(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "MessageHandlerConfiguration{" +
                "number=" + number +
                '}';
    }
}
