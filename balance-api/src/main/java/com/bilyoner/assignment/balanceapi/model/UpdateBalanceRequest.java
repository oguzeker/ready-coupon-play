package com.bilyoner.assignment.balanceapi.model;

import com.bilyoner.assignment.balanceapi.model.enums.TransactionTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBalanceRequest {

    @ApiModelProperty(value = "Id of the user balance entity", example = "1", position = 1)
    private Long userId;

    @ApiModelProperty(value = "User balance amount", example = "50", position = 2)
    private BigDecimal amount;

    @ApiModelProperty(value = "Balance update operation transaction id",
            example = "15ae51ab-bd23-4a88-925b-fb660c2b1581", position = 3)
    private String transactionId;

    @ApiModelProperty(value = "Balance update operation transaction type", example = "WITHDRAW", position = 4)
    private TransactionTypeEnum transactionType;

}
