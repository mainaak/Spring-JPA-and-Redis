package com.mainaak.demo.configuration;

import com.mainaak.demo.entity.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class Everything {

    private final RedisConfig redisConfig;

    @Autowired
    public Everything(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }

    @Value("${exception.timezone:Z}")
    public String timezone;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){

        RedisStandaloneConfiguration rc = new RedisStandaloneConfiguration();
        rc.setHostName(redisConfig.getHost());
        rc.setPort(redisConfig.getPort());

        return new JedisConnectionFactory(rc);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(){

        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());

        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

}
