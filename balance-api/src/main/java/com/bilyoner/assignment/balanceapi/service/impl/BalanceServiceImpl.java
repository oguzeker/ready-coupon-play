package com.bilyoner.assignment.balanceapi.service.impl;

import com.bilyoner.assignment.balanceapi.exception.BalanceApiException;
import com.bilyoner.assignment.balanceapi.exception.ErrorCodeEnum;
import com.bilyoner.assignment.balanceapi.model.UpdateBalanceRequest;
import com.bilyoner.assignment.balanceapi.model.UserBalanceDTO;
import com.bilyoner.assignment.balanceapi.model.enums.TransactionTypeEnum;
import com.bilyoner.assignment.balanceapi.persistence.entity.UserBalanceEntity;
import com.bilyoner.assignment.balanceapi.persistence.entity.UserBalanceHistoryEntity;
import com.bilyoner.assignment.balanceapi.persistence.repository.UserBalanceHistoryRepository;
import com.bilyoner.assignment.balanceapi.persistence.repository.UserBalanceRepository;
import com.bilyoner.assignment.balanceapi.service.BalanceService;
import com.bilyoner.assignment.balanceapi.util.BigDecimalUtil;
import com.bilyoner.assignment.balanceapi.util.RedissonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final UserBalanceRepository userBalanceRepository;
    private final UserBalanceHistoryRepository userBalanceHistoryRepository;
    private final RedissonClient redissonClient;
    private final ModelMapper mapper;

    public UserBalanceDTO getUserBalanceWithHistory(Long userId) {
        log.info("getUserBalanceWithHistory-begin {}", kv("userId", userId));
        UserBalanceEntity entity = userBalanceRepository.getUserBalanceEntitiesWithHistory(userId).orElseThrow(() ->
                new BalanceApiException(ErrorCodeEnum.ENTITY_NOT_FOUND_VALUE, userId));

        UserBalanceDTO response = mapper.map(entity, UserBalanceDTO.class);
        log.info("getUserBalanceWithHistory-end {}", kv("response", response));
        return response;
    }

    public void updateBalance(UpdateBalanceRequest updateBalanceRequest) {
        log.info("updateBalance-begin {}", kv("updateBalanceRequest", updateBalanceRequest));
        RedissonUtil.executeWithFairLock(redissonClient, this::performBalanceUpdate, updateBalanceRequest);
        log.info("updateBalance-end");
    }

    private void performBalanceUpdate(UpdateBalanceRequest updateBalanceRequest) {
        Long userId = updateBalanceRequest.getUserId();
        UserBalanceEntity entity = userBalanceRepository.findById(userId).orElseThrow(() ->
                new BalanceApiException(ErrorCodeEnum.ENTITY_NOT_FOUND_VALUE, userId));

        BigDecimal currentBalanceAmount = entity.getAmount();
        BigDecimal newBalanceAmount = calculateNewBalanceAmount(currentBalanceAmount, updateBalanceRequest.getAmount(),
                updateBalanceRequest.getTransactionType());

        entity.setAmount(newBalanceAmount);

        UserBalanceHistoryEntity historyRecord = buildHistoryRecord(updateBalanceRequest, currentBalanceAmount,
                newBalanceAmount, entity);

        userBalanceRepository.save(entity);
        userBalanceHistoryRepository.save(historyRecord);
    }

    private UserBalanceHistoryEntity buildHistoryRecord(UpdateBalanceRequest updateBalanceRequest,
                                                        BigDecimal previousBalanceAmount, BigDecimal newBalanceAmount,
                                                        UserBalanceEntity userBalanceEntity) {
        UserBalanceHistoryEntity entity = mapper.map(updateBalanceRequest, UserBalanceHistoryEntity.class);
        entity.setPreviousBalanceAmount(previousBalanceAmount);
        entity.setNewBalanceAmount(newBalanceAmount);
        entity.setUserBalanceEntity(userBalanceEntity);

        return entity;
    }

    private BigDecimal calculateNewBalanceAmount(BigDecimal currentBalanceAmount, BigDecimal balanceChange,
                                                 TransactionTypeEnum transactionType) {
        BigDecimal newBalanceAmount = currentBalanceAmount;
        switch (transactionType) {
            case DEPOSIT:
                newBalanceAmount = newBalanceAmount.add(balanceChange);
                break;
            case WITHDRAW:
            case REFUND:
                checkBalanceSufficient(currentBalanceAmount, balanceChange);
                newBalanceAmount = newBalanceAmount.subtract(balanceChange);
                break;
        }
        return newBalanceAmount;
    }

    private void checkBalanceSufficient(BigDecimal currentBalanceAmount, BigDecimal balanceChange) {
        BigDecimalUtil.BDComparisonResult comparisonResult = BigDecimalUtil.compare(
                currentBalanceAmount, balanceChange);
        if (comparisonResult == BigDecimalUtil.BDComparisonResult.SECOND_VALUE_GREATER) {
            throw new BalanceApiException(ErrorCodeEnum.INSUFFICIENT_BALANCE, balanceChange, currentBalanceAmount);
        }
    }

}
