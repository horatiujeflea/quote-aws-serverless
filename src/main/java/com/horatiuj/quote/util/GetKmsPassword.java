package com.horatiuj.quote.util;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.DecryptRequest;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.function.Supplier;

public interface GetKmsPassword extends Supplier<String> {
    @Override
    default String get() {
        String rawPassword = System.getenv(key());
        AWSKMS kms = AWSKMSClientBuilder.defaultClient();
        ByteBuffer plaintext = kms.decrypt(new DecryptRequest()
                .withCiphertextBlob(ByteBuffer.wrap(Base64.getDecoder().decode(rawPassword.getBytes())))).getPlaintext();
        return StandardCharsets.UTF_8.decode(plaintext).toString();
    }

    String key();
}
