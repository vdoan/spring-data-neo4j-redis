package com.tutorial.rediscache.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@Slf4j
@EnableRedisRepositories
@Data
public class RedisConfig {

  public static final String USER_CACHE_NAME = "user";
  public static final String CONTACT_CACHE_NAME = "contact";

  @Value("${spring.data.redis.host}")
  private String redisHost;

  @Value("${spring.data.redis.port}")
  private int redisPort;

  private long timeoutSeconds = 60;
  private long timeoutMinutes = 60;

  @Bean
  public JedisConnectionFactory redisConnectionFactory() {

    RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
    return new JedisConnectionFactory(config);
  }

  @Bean
  public RedisSerializer redisStringSerializer() {
    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    return stringRedisSerializer;
  }

  @Bean
  public RedisCacheManager cacheManager() {
    RedisCacheConfiguration cacheConfig = myDefaultCacheConfig(Duration.ofDays(30))
            .disableCachingNullValues();

    return RedisCacheManager.builder(redisConnectionFactory())
        .cacheDefaults(cacheConfig)
//        .withCacheConfiguration("users", myDefaultCacheConfig(Duration.ofMinutes(60)))
//        .withCacheConfiguration("user", myDefaultCacheConfig(Duration.ofMinutes(60)))
        .build();
  }

  private RedisCacheConfiguration myDefaultCacheConfig(Duration duration) {
    return RedisCacheConfiguration
        .defaultCacheConfig()
            .disableCachingNullValues()
        .entryTtl(duration)
        .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate() {

    final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory());

    final var jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }
}
