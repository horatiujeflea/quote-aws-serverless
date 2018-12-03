package com.horatiuj.quote.core;

import okhttp3.MediaType;

public class Constants {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final String PROFILE_ID = "1";
    public static final String FIXED_RATE_TYPE = "FIXED";

    public static final String TRANSFERWISE_BASE_URL = "https://api.sandbox.transferwise.tech";
    public static final String TRANSFERWISE_API_VERSION = "v1";
    public static final String TRANSFERWISE_QUOTES_API = "quotes";
    public static final String TRANSFERWISE_APIKEY_NAME = "TRANSFERWISE_APIKEY";

    public static final String REDIS_URL_NAME = "REDIS_URL";
    public static final String REDIS_PASSWORD_NAME = "REDIS_PASSWORD";
}
