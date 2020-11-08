package com.bilyoner.assignment.balanceapi.model;

import com.bilyoner.assignment.balanceapi.model.enums.TransactionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBalanceHistoryDTO {

    private Long id;

    private BigDecimal amount;

    private BigDecimal previousBalanceAmount;

    private BigDecimal newBalanceAmount;

    private String transactionId;

    private TransactionTypeEnum transactionType;

    private LocalDateTime createDate;

    private UserBalanceDTO userBalanceEntity;

}
