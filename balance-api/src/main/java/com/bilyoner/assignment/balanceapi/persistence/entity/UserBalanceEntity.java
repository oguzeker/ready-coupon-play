package com.bilyoner.assignment.balanceapi.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBalanceEntity {

    @Id
    @GeneratedValue
    private Long userId;

    private BigDecimal amount;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "userBalanceEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<UserBalanceHistoryEntity> historyEntities;

}
