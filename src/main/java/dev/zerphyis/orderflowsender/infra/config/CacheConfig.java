package dev.zerphyis.orderflowsender.infra.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(
            RedisConnectionFactory redisConnectionFactory
    ) {

        RedisSerializer<Object> serializer =
                new RedisSerializer<>() {

                    private final ObjectMapper objectMapper =
                            new ObjectMapper();

                    @Override
                    public byte[] serialize(Object value) {

                        if (value == null) {
                            return new byte[0];
                        }

                        return objectMapper.writeValueAsBytes(value);
                    }

                    @Override
                    public Object deserialize(byte[] bytes) {

                        if (bytes == null || bytes.length == 0) {
                            return null;
                        }

                        return objectMapper.readValue(
                                bytes,
                                Object.class
                        );
                    }
                };

        RedisCacheConfiguration configuration =
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(10))
                        .serializeValuesWith(
                                RedisSerializationContext
                                        .SerializationPair
                                        .fromSerializer(serializer)
                        );

        return RedisCacheManager.builder(
                        redisConnectionFactory
                )
                .cacheDefaults(configuration)
                .build();
    }
}