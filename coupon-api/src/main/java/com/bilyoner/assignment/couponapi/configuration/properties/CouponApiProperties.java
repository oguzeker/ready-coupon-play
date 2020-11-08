package com.bilyoner.assignment.couponapi.configuration.properties;

import com.bilyoner.assignment.couponapi.model.enums.EventTypeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "coupon-api")
public class CouponApiProperties {

    private List<EventTypeEnum> eventTypesToCheck;

    private int eventTypeCombinationCount;

    private BigDecimal couponCost;

    private Long couponCancellationAllowedDurationMins;

}
