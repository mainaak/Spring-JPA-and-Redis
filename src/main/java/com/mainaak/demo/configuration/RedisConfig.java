package com.mainaak.demo.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "config.redis")
public class RedisConfig {
    private String host;
    private Integer port;
    private String timeout;
    private String prefix;
}
