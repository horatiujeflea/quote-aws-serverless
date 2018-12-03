package com.horatiuj.quote.dal;

import com.horatiuj.quote.core.Constants;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;

public enum  RedisDbClient {
    INSTANCE;

    public static final String PROTOCOL = "redis://";
    private final StatefulRedisConnection<String, String> connection;

    RedisDbClient() {
        RedisClient redisClient = RedisClient.create(
                String.join("",PROTOCOL, new GetRedisPassword().get(), "@", System.getenv(Constants.REDIS_URL_NAME)));
        connection = redisClient.connect();
    }

    public StatefulRedisConnection<String, String> getConnection() {
        return connection;
    }
}
