package com.ashwin.dropwizard.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;

    public User() {
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", name='" + name + '\''
                + "}";
    }

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        String result = null;
        try {
            result = objectMapper.writeValueAsString(this);
        } catch(JsonProcessingException e) {
        }
        return result;
    }

    public User fromJson(String value) {
        ObjectMapper objectMapper = new ObjectMapper();
        User result = null;
        try {
            result = objectMapper.readValue(value, User.class);
        } catch(IOException e) {
        }
        return result;
    }
}
