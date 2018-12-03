package com.horatiuj.quote.transferwise;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.DecryptRequest;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.function.Supplier;

import static com.horatiuj.quote.core.Constants.TRANSFERWISE_APIKEY_NAME;

public class GetTransferWiseApiKey implements Supplier<String> {
    @Override
    public String get() {
        String rawPassword = System.getenv(TRANSFERWISE_APIKEY_NAME);
        AWSKMS kms = AWSKMSClientBuilder.defaultClient();
        ByteBuffer plaintext = kms.decrypt(new DecryptRequest()
                .withCiphertextBlob(ByteBuffer.wrap(Base64.getDecoder().decode(rawPassword.getBytes())))).getPlaintext();
        return StandardCharsets.UTF_8.decode(plaintext).toString();
    }
}
