package com.bilyoner.assignment.balanceapi.configuration;

import com.bilyoner.assignment.balanceapi.configuration.properties.RedissonProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RedissonConfig {

    private final RedissonProperties properties;

    @Bean
    public Config config() {
        Config config = new Config();
        config.setThreads(properties.getThreads())
                .setNettyThreads(properties.getNettyThreads())
                .useSingleServer()
                .setAddress(properties.getHost())
                .setConnectionMinimumIdleSize(properties.getConnectionMinimumIdleSize())
                .setConnectionPoolSize(properties.getConnectionPoolSize())
                .setTimeout(properties.getTimeout());

        return config;
    }

    @Bean
    public RedissonClient redissonClient(Config config) {
        return Redisson.create(config);

    }
}
