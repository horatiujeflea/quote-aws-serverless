package com.horatiuj.quote.core;

import com.fasterxml.jackson.databind.ObjectMapper;

public enum JsonMapper {
    INSTANCE;

    private final ObjectMapper mapper;

    JsonMapper() {
        mapper = new ObjectMapper();
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

}
