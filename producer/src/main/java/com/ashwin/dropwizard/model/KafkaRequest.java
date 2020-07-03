package com.ashwin.dropwizard.model;

public class KafkaRequest {
    private String topic;
    private User user;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "KafkaRequest{" +
                "topic='" + topic + '\'' +
                ", user=" + String.valueOf(user) +
                '}';
    }
}
