package com.tutorial.rediscache.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.tutorial.rediscache.dao.entity.party.User;

import java.time.Duration;
import java.util.Map;

@Configuration
@Slf4j
@EnableRedisRepositories
@Data
public class RedisConfig {
  @Value("${spring.redis.host}")
  private String redisHost;

  @Value("${spring.redis.port}")
  private int redisPort;

  private long timeoutSeconds = 60;
  private long timeoutMinutes = 60;

//  @Bean
//  public RedisConnectionFactory redisConnectionFactory() {
//    log.info("Redis (/Jedis) configuration enabled. With cache timeout " + timeoutMinutes + " minutes.");
//    RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
//    redisConfig.setHostName(redisHost);
//    redisConfig.setPort(redisPort);
//    return new JedisConnectionFactory(redisConfig);
//  }

//  @Bean
//  public LettuceConnectionFactory redisConnectionFactory() {
//    log.info("Redis (/Lettuce) configuration enabled. With cache timeout " + timeoutMinutes + " minutes.");
//    RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisHost, redisPort);
//
//    return new LettuceConnectionFactory(configuration);
//  }

  @Bean
  public JedisConnectionFactory redisConnectionFactory() {

    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
    jedisConnectionFactory.setUsePool(true);
    return jedisConnectionFactory;
  }

//  @Bean
//  public JedisConnectionFactory jedisConnectionFactory() {
//    RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
//    redisConfig.setHostName(redisHost);
//    redisConfig.setPort(redisPort);
//    return new JedisConnectionFactory(redisConfig);
//  }

  @Bean
  public RedisSerializer redisStringSerializer() {
    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    return stringRedisSerializer;
  }

//  @Bean
//  public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//    return RedisCacheManager.create(connectionFactory);
//  }

//  @Bean
//  public RedisCacheConfiguration cacheConfiguration() {
//    return RedisCacheConfiguration.defaultCacheConfig()
//            .entryTtl(Duration.ofMinutes(timeoutMinutes))
//            .disableCachingNullValues()
//            .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
//  }
  @Bean
  public RedisCacheManager cacheManager() {
    RedisCacheConfiguration cacheConfig = myDefaultCacheConfig(Duration.ofMinutes(10)).disableCachingNullValues();

    return RedisCacheManager.builder(redisConnectionFactory())
        .cacheDefaults(cacheConfig)
//        .withCacheConfiguration("users", myDefaultCacheConfig(Duration.ofMinutes(60)))
//        .withCacheConfiguration("user", myDefaultCacheConfig(Duration.ofMinutes(60)))
        .build();
  }

//
//  @Bean
//  public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
//    return (builder) -> builder
//            .withCacheConfiguration("user",
//                    RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)))
//            .withCacheConfiguration("customerCache",
//                    RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)));
//  }
//

  private RedisCacheConfiguration myDefaultCacheConfig(Duration duration) {
    return RedisCacheConfiguration
        .defaultCacheConfig()
            .disableCachingNullValues()
        .entryTtl(duration)
        .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
  }

//  @Bean
//  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory cf) {
//    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//    redisTemplate.setConnectionFactory(redisConnectionFactory());
//    redisTemplate.setKeySerializer(new StringRedisSerializer());
//    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//    redisTemplate.setHashKeySerializer(new JdkSerializationRedisSerializer());
//    redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
//    redisTemplate.setEnableTransactionSupport(true);
//    redisTemplate.afterPropertiesSet();
//    return redisTemplate;
//  }

//  @Bean
//  public RedisTemplate<?, ?> redisTemplate() {
//    RedisTemplate<?, ?> template = new RedisTemplate<>();
//    template.setConnectionFactory(redisConnectionFactory());
//
//    return template;
//  }

//  @Bean
//  public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
//    RedisTemplate<?, ?> template = new RedisTemplate<>();
//    template.setConnectionFactory(connectionFactory);
//
//    return template;
//  }

//  @Bean
//  public RedisTemplate<String, Object> redisTemplate() {
//    final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
//    template.setKeySerializer(new StringRedisSerializer());
//    template.setHashKeySerializer(new StringRedisSerializer());
//    template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//    template.setConnectionFactory(redisConnectionFactory());
//    return template;
//  }

//  @Bean
//  public RedisTemplate<String, Map<String, Object>> redisTemplateStandAlone(RedisConnectionFactory redisConnectionFactory) {
//    RedisTemplate<String, Map<String, Object>> redisTemplate = new RedisTemplate<>();
//    redisTemplate.setConnectionFactory(redisConnectionFactory);
//    redisTemplate.setKeySerializer(new StringRedisSerializer());
//    redisTemplate.setValueSerializer(new StringRedisSerializer());
//    redisTemplate.afterPropertiesSet();
//    return redisTemplate;
//  }

//  @Bean
//  public StringRedisTemplate redisTemplate() {
//    StringRedisTemplate template = new StringRedisTemplate();
//    template.setConnectionFactory(jedisConnectionFactory());
//
//    return template;
//  }

//  @Bean
//  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory cf, RedisSerializer redisSerializer) {
//    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//    redisTemplate.setConnectionFactory(cf);
//    redisTemplate.afterPropertiesSet();
//    redisTemplate.setDefaultSerializer(redisSerializer);
//    return redisTemplate;
//  }

//  @Bean
//  public RedisTemplate<?, ?> redisTemplate() {
//    final RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
//    redisTemplate.setConnectionFactory(redisConnectionFactory());
//    redisTemplate.setEnableTransactionSupport(true);
//    redisTemplate.setKeySerializer(new StringRedisSerializer());
//    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//    return redisTemplate;
//  }

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
