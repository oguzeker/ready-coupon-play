package com.bilyoner.assignment.balanceapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBalanceDTO {

    private Long userId;

    private BigDecimal amount;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("userBalanceEntity")
    private List<UserBalanceHistoryDTO> historyEntities;

}
