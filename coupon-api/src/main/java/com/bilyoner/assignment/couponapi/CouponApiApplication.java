package com.bilyoner.assignment.couponapi;

import com.bilyoner.assignment.couponapi.configuration.properties.BalancesApiProperties;
import com.bilyoner.assignment.couponapi.configuration.properties.CouponApiProperties;
import com.bilyoner.assignment.couponapi.configuration.properties.SwaggerProperties;
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
        BalancesApiProperties.class,
        CouponApiProperties.class,
        SwaggerProperties.class
})
public class CouponApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponApiApplication.class, args);
    }

}
