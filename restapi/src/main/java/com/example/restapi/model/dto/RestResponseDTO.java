package com.example.restapi.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class RestResponseDTO<T> {

    public enum TypeResult {
        SUCCESS, ERROR
    }

    private TypeResult status;

    private Map<String, Object> messages;

    private T data;

    private Map<String, Object> metadata;

    public void ok() {
        this.status = TypeResult.SUCCESS;
        this.messages = new HashMap<>();
        this.metadata = new HashMap<>();
    }

    public void ok(T data) {
        this.status = TypeResult.SUCCESS;
        this.data = data;
        this.messages = new HashMap<>();
        this.metadata = new HashMap<>();
    }

    public void fail() {
        this.status = TypeResult.ERROR;
        this.messages = new HashMap<>();
        this.metadata = new HashMap<>();
    }

    public void fail(HashMap<String, Object> hashMap) {
        this.status = TypeResult.ERROR;
        this.messages = hashMap;
        this.metadata = new HashMap<>();
    }

    public void addMetadata(String key, String value) {
        if (metadata == null) {
            this.metadata = new HashMap<>();
        }
        metadata.put(key, value);
    }

}
