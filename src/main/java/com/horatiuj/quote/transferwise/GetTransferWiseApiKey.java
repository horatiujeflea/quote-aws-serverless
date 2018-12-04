package com.horatiuj.quote.transferwise;

import com.horatiuj.quote.util.GetKmsPassword;

import static com.horatiuj.quote.core.Constants.TRANSFERWISE_APIKEY_NAME;

public class GetTransferWiseApiKey implements GetKmsPassword {

    @Override
    public String key() {
        return TRANSFERWISE_APIKEY_NAME;
    }
}
