package org.prography.kagongsillok.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableCaching
@Configuration
@RequiredArgsConstructor
public class CacheConfig extends CachingConfigurerSupport {

    private static final long DEFAULT_TTL_SECONDS = 3600L;

    private final ObjectMapper objectMapper;
    private final RedisConnectionFactory redisConnectionFactory;


    @Override
    public CacheManager cacheManager() {
        final RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(
                        SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper))
                )
                .disableCachingNullValues()
                .entryTtl(Duration.ofSeconds(DEFAULT_TTL_SECONDS));
        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
                .withInitialCacheConfigurations(cacheTtlConfiguration())
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }

    /**
     * 별도의 TTL을 설정하려면 여기에 추가
     *
     * ex)
     * cacheTtlConfiguration.put(key, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(seconds)));
     */
    private Map<String, RedisCacheConfiguration> cacheTtlConfiguration() {
        final Map<String, RedisCacheConfiguration> cacheTtlConfiguration = new HashMap<>();

        return cacheTtlConfiguration;
    }
}
