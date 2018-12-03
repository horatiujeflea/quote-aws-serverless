package com.horatiuj.quote.lambda;

import lombok.Data;

import java.util.Map;

@Data
public class Response {
    private final int statusCode;
    private final Map<String, String> headers;
    private final String body;
}
