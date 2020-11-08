package com.bilyoner.assignment.balanceapi.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "redis")
public class RedissonProperties {

    private String host;

    private int threads;

    private int nettyThreads;

    private int connectionMinimumIdleSize;

    private int connectionPoolSize;

    private int timeout;

}
