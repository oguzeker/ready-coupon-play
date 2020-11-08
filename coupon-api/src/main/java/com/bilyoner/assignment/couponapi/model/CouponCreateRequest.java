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
public class CouponCreateRequest {

    @ApiModelProperty(value = "Id list of the event entities", example = "[1,2,3]", position = 1)
    private List<Long> eventIds;

}
