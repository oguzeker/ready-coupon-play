package com.bilyoner.assignment.couponapi.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "balances-api")
public class BalancesApiProperties {

    private String baseUrl;

}
