package com.horatiuj.quote.transferwise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.horatiuj.quote.core.HttpClient;
import com.horatiuj.quote.core.JsonMapper;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Function;

import static com.horatiuj.quote.core.Constants.*;

public class GetTransferWiseQuote implements Function<TransferWiseRequest, Optional<TransferWiseResponse>> {
    private static final Logger LOG = LoggerFactory.getLogger(GetTransferWiseQuote.class);
    private static final ObjectMapper JSON_MAPPER = JsonMapper.INSTANCE.getMapper();

    @Override
    public Optional<TransferWiseResponse> apply(TransferWiseRequest transferwiseRequest) {
        try {
            RequestBody body = RequestBody.create(JSON, JSON_MAPPER.writeValueAsString(transferwiseRequest));

            Request request = new Request.Builder()
                    .header("Authorization", String.join(" ", "Bearer", new GetTransferWiseApiKey().get()))
                    .url(String.join("/", TRANSFERWISE_BASE_URL, TRANSFERWISE_API_VERSION, TRANSFERWISE_QUOTES_API))
                    .post(body)
                    .build();

            Response response = HttpClient.INSTANCE.getHttpClient().newCall(request).execute();

            if (response.code() != 200 && response.code() != 201) {
                throw new FailedTransferwiseResponseException("Error code " + response.code());
            }

            if (response.body() == null) {
                throw new FailedTransferwiseResponseException("Empty response body");
            } else {
                return Optional.ofNullable(JSON_MAPPER.readValue(response.body().string(), TransferWiseResponse.class));
            }
        } catch (Exception e) {
            LOG.error("Failed to retrieve quote from MoneyTransfer", e);
            return Optional.empty();
        }
    }

    private static class FailedTransferwiseResponseException extends RuntimeException {
        FailedTransferwiseResponseException(String message) {
            super(message);
        }
    }
}
