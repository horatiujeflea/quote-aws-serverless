package com.horatiuj.quote.dal.payment_log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.horatiuj.quote.core.JsonMapper;
import com.horatiuj.quote.dal.RedisDbClient;
import com.horatiuj.quote.dal.dao.PaymentLog;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.function.Consumer;

public class SavePaymentLog implements Consumer<PaymentLog> {
    private static final ObjectMapper JSON_MAPPER = JsonMapper.INSTANCE.getMapper();
    private static final String PREFIX = "payment";


    @Override
    public void accept(PaymentLog paymentLog) {
        StatefulRedisConnection<String, String> connection = RedisDbClient.INSTANCE.getConnection();
        RedisCommands<String, String> syncCommands = connection.sync();

        try {
            syncCommands.set(String.join("-", PREFIX, paymentLog.getId()),
                    JSON_MAPPER.writeValueAsString(paymentLog));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
