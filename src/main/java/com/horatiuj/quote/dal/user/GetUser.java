package com.horatiuj.quote.dal.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.horatiuj.quote.core.JsonMapper;
import com.horatiuj.quote.dal.RedisDbClient;
import com.horatiuj.quote.dal.dao.User;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;

public class GetUser implements Function<String, Optional<User>> {
    private static final ObjectMapper JSON_MAPPER = JsonMapper.INSTANCE.getMapper();
    private static final Logger LOG = LoggerFactory.getLogger(GetUser.class);
    private static final String PREFIX = "user";

    @Override
    public Optional<User> apply(String userId) {
        StatefulRedisConnection<String, String> connection = RedisDbClient.INSTANCE.getConnection();
        RedisCommands<String, String> syncCommands = connection.sync();
        return Optional.ofNullable(syncCommands.get(String.join("-", PREFIX, userId))).map(ju -> {
            try {
                return JSON_MAPPER.readValue(ju, User.class);
            } catch (IOException e) {
                LOG.error("Failed to parse user from Redis", e);
                return null;
            }
        });
    }

}
