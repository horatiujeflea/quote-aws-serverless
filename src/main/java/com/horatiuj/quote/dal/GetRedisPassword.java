package com.horatiuj.quote.dal;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.DecryptRequest;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.function.Supplier;

import static com.horatiuj.quote.core.Constants.REDIS_PASSWORD_NAME;

public class GetRedisPassword implements Supplier<String> {
    @Override
    public String get() {
        String rawPassword = System.getenv(REDIS_PASSWORD_NAME);
        AWSKMS kms = AWSKMSClientBuilder.defaultClient();
        ByteBuffer plaintext = kms.decrypt(new DecryptRequest()
                .withCiphertextBlob(ByteBuffer.wrap(Base64.getDecoder().decode(rawPassword.getBytes())))).getPlaintext();
        return StandardCharsets.UTF_8.decode(plaintext).toString();
    }
}
