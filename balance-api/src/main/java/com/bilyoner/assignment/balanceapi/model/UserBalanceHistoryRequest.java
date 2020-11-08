package com.bilyoner.assignment.balanceapi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBalanceHistoryRequest {

    @ApiModelProperty(value = "Id of the user balance entity", example = "1", position = 1)
    private Long userId;

}
