package com.horatiuj.quote.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.horatiuj.quote.payment.ProcessPayment;
import com.horatiuj.quote.core.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class PostPaymentLambda implements RequestStreamHandler {
    private static final Logger LOG = LoggerFactory.getLogger(PostPaymentLambda.class);
    private static final ObjectMapper MAPPER = JsonMapper.INSTANCE.getMapper();

    @Override
    public void handleRequest(InputStream is, OutputStream os, Context context) throws IOException {
        JsonNode root = MAPPER.readTree(is);
        JsonNode body = root.at("/body");

        PostPaymentInput postPaymentInput;
        try {
            postPaymentInput = MAPPER.readValue(body.textValue(), PostPaymentInput.class);
        } catch (Exception e) {
            LOG.error("Input is invalid or missing", e);
            sendResponse(os, 400, "Invalid input, format is : ....");
            return;
        }

        String userId = postPaymentInput.getUserId();
        final Double paymentAmount = postPaymentInput.getPaymentAmount();

        if (paymentAmount == null || paymentAmount < 0.0) {
            sendResponse(os, 400, "The payment amount is missing or is a negative number");
            return;
        }

        if (userId == null || userId.isEmpty()) {
            sendResponse(os, 400, "User id is missing");
            return;
        }

        try {
            final String uuid = UUID.randomUUID().toString();
            new ProcessPayment().accept(ProcessPayment.Input.builder()
                    .userId(userId)
                    .paymentId(uuid)
                    .paymentAmount(paymentAmount)
                    .build());
            sendResponse(os, 200, uuid);
        } catch (Exception e) {
            LOG.error("Internal server error for post payment", e);
            sendResponse(os, 500, "Internal server error");
        }
    }

    private void sendResponse(OutputStream os, int statusCode, String message) throws IOException {
        MAPPER.writeValue(os, new Response(statusCode, null, message));
    }
}
