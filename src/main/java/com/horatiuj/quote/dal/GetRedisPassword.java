package com.horatiuj.quote.dal;

import com.horatiuj.quote.util.GetKmsPassword;

import static com.horatiuj.quote.core.Constants.REDIS_PASSWORD_NAME;

public class GetRedisPassword implements GetKmsPassword {

    @Override
    public String key() {
        return REDIS_PASSWORD_NAME;
    }
}
