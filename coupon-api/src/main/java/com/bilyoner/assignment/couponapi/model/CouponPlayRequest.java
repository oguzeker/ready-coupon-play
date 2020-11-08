package com.bilyoner.assignment.couponapi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponPlayRequest {

    @ApiModelProperty(value = "Id of the user balance entity", example = "1", position = 1)
    private Long userId;

    @ApiModelProperty(value = "Id list of the coupon entities", example = "[1,2,3]", position = 2)
    private List<Long> couponIds;

}
