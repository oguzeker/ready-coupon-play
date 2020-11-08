package com.bilyoner.assignment.balanceapi.service.impl;

import com.bilyoner.assignment.balanceapi.model.enums.TransactionTypeEnum;
import com.bilyoner.assignment.balanceapi.persistence.entity.UserBalanceEntity;
import com.bilyoner.assignment.balanceapi.persistence.entity.UserBalanceHistoryEntity;
import com.bilyoner.assignment.balanceapi.persistence.repository.UserBalanceHistoryRepository;
import com.bilyoner.assignment.balanceapi.persistence.repository.UserBalanceRepository;
import com.bilyoner.assignment.balanceapi.service.StartupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StartupServiceImpl implements StartupService {

    private final UserBalanceRepository userBalanceRepository;
    private final UserBalanceHistoryRepository userBalanceHistoryRepository;

    /**
     * Populates sample balance information to user balance table.
     */
    @PostConstruct
    private void initDb() {

        if (userBalanceRepository.count() <= 0) {
            createUserBalances();
        }
    }

    private void createUserBalances() {
        UserBalanceEntity userBalanceEntity1 = UserBalanceEntity.builder()
                .userId(1L)
                .amount(BigDecimal.valueOf(10))
                .build();
        UserBalanceHistoryEntity userBalanceHistoryEntity1 = UserBalanceHistoryEntity.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(10))
                .previousBalanceAmount(BigDecimal.ZERO)
                .newBalanceAmount(BigDecimal.valueOf(10))
                .transactionId(createRandomTransactionId())
                .transactionType(TransactionTypeEnum.DEPOSIT)
                .userBalanceEntity(userBalanceEntity1)
                .build();

        UserBalanceEntity userBalanceEntity2 = UserBalanceEntity.builder()
                .userId(2L)
                .amount(BigDecimal.valueOf(20))
                .build();
        UserBalanceHistoryEntity userBalanceHistoryEntity2 = UserBalanceHistoryEntity.builder()
                .id(2L)
                .amount(BigDecimal.valueOf(20))
                .previousBalanceAmount(BigDecimal.ZERO)
                .newBalanceAmount(BigDecimal.valueOf(20))
                .transactionId(createRandomTransactionId())
                .transactionType(TransactionTypeEnum.DEPOSIT)
                .userBalanceEntity(userBalanceEntity2)
                .build();

        UserBalanceEntity userBalanceEntity3 = UserBalanceEntity.builder()
                .userId(3L)
                .amount(BigDecimal.valueOf(30))
                .build();
        UserBalanceHistoryEntity userBalanceHistoryEntity3 = UserBalanceHistoryEntity.builder()
                .id(3L)
                .amount(BigDecimal.valueOf(30))
                .previousBalanceAmount(BigDecimal.ZERO)
                .newBalanceAmount(BigDecimal.valueOf(30))
                .transactionId(createRandomTransactionId())
                .transactionType(TransactionTypeEnum.DEPOSIT)
                .userBalanceEntity(userBalanceEntity3)
                .build();

        UserBalanceEntity userBalanceEntity4 = UserBalanceEntity.builder()
                .userId(4L)
                .amount(BigDecimal.valueOf(40))
                .build();
        UserBalanceHistoryEntity userBalanceHistoryEntity4 = UserBalanceHistoryEntity.builder()
                .id(4L)
                .amount(BigDecimal.valueOf(40))
                .previousBalanceAmount(BigDecimal.ZERO)
                .newBalanceAmount(BigDecimal.valueOf(40))
                .transactionId(createRandomTransactionId())
                .transactionType(TransactionTypeEnum.DEPOSIT)
                .userBalanceEntity(userBalanceEntity4)
                .build();

        userBalanceRepository.saveAll(Arrays.asList(userBalanceEntity1, userBalanceEntity2,
                userBalanceEntity3, userBalanceEntity4));
        userBalanceHistoryRepository.saveAll(Arrays.asList(userBalanceHistoryEntity1, userBalanceHistoryEntity2,
                userBalanceHistoryEntity3, userBalanceHistoryEntity4));
    }

    private String createRandomTransactionId() {
        return UUID.randomUUID().toString();
    }

}
