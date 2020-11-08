package com.bilyoner.assignment.couponapi.service.impl;

import com.bilyoner.assignment.couponapi.client.BalancesApiClient;
import com.bilyoner.assignment.couponapi.client.model.UpdateBalanceRequest;
import com.bilyoner.assignment.couponapi.client.model.enums.TransactionTypeEnum;
import com.bilyoner.assignment.couponapi.service.BalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final BalancesApiClient client;

    public void updateBalance(Long userId, BigDecimal amount, String transactionId,
                              TransactionTypeEnum transactionType) {
        log.info("updateBalance-begin {} {} {} {}", kv("userId", userId), kv("amount", amount), kv("transactionId",
                transactionId), kv("transactionType", transactionType));

        client.updateBalance(UpdateBalanceRequest.builder()
                .userId(userId)
                .amount(amount)
                .transactionId(transactionId)
                .transactionType(transactionType)
                .build());

        log.info("updateBalance-end");
    }
}
