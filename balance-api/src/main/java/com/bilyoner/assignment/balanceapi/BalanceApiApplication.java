package com.bilyoner.assignment.balanceapi;

import com.bilyoner.assignment.balanceapi.configuration.properties.RedissonProperties;
import com.bilyoner.assignment.balanceapi.configuration.properties.SwaggerProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableJpaAuditing
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties({
        RedissonProperties.class,
        SwaggerProperties.class
})
public class BalanceApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BalanceApiApplication.class, args);
    }
}
