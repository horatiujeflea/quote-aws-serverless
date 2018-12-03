package com.horatiuj.quote.core;

import okhttp3.OkHttpClient;

public enum  HttpClient {
    INSTANCE;

    private final OkHttpClient httpClient;

    HttpClient() {
        httpClient = new OkHttpClient();
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }
}
