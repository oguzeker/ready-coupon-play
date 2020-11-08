package com.bilyoner.assignment.couponapi.service.impl;

import com.bilyoner.assignment.couponapi.client.model.enums.TransactionTypeEnum;
import com.bilyoner.assignment.couponapi.configuration.properties.CouponApiProperties;
import com.bilyoner.assignment.couponapi.entity.CouponEntity;
import com.bilyoner.assignment.couponapi.entity.CouponSelectionEntity;
import com.bilyoner.assignment.couponapi.entity.EventEntity;
import com.bilyoner.assignment.couponapi.exception.CouponApiException;
import com.bilyoner.assignment.couponapi.exception.ErrorCodeEnum;
import com.bilyoner.assignment.couponapi.model.CouponCreateRequest;
import com.bilyoner.assignment.couponapi.model.CouponDTO;
import com.bilyoner.assignment.couponapi.model.CouponPlayRequest;
import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import com.bilyoner.assignment.couponapi.model.enums.EventTypeEnum;
import com.bilyoner.assignment.couponapi.repository.CouponRepository;
import com.bilyoner.assignment.couponapi.repository.CouponSelectionRepository;
import com.bilyoner.assignment.couponapi.service.BalanceService;
import com.bilyoner.assignment.couponapi.service.CouponService;
import com.bilyoner.assignment.couponapi.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.paukov.combinatorics3.Generator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private static final int DEFAULT_MBS = 0;
    private final CouponRepository couponRepository;
    private final CouponSelectionRepository couponSelectionRepository;
    private final EventService eventService;
    private final BalanceService balanceService;
    private final CouponApiProperties properties;
    private final ModelMapper mapper;

    public Page<CouponDTO> getAllCouponsByCouponStatus(CouponStatusEnum couponStatus, int pageIndex, int pageSize) {
        log.info("getAllCouponsByCouponStatus-begin {} {} {}", kv("couponStatus", couponStatus),
                kv("pageIndex", pageIndex), kv("pageSize", pageSize));

        Page<CouponDTO> response = couponRepository.findByStatus(couponStatus,
                PageRequest.of(pageIndex, pageSize))
                .map(entity -> {
                    CouponDTO dto = mapper.map(entity, CouponDTO.class);
                    dto.setEventIds(new ArrayList<>());
                    entity.getSelections().forEach(s -> dto.getEventIds().add(s.getEvent().getId()));
                    return dto;
                });

        log.info("getAllCouponsByCouponStatus-end {}", kv("response", response));
        return response;
    }

    public CouponDTO createCoupon(CouponCreateRequest couponCreateRequest) {
        log.info("createCoupon-begin {}", kv("couponCreateRequest", couponCreateRequest));

        List<Long> eventIds = couponCreateRequest.getEventIds();
        List<EventEntity> events = eventService.getEventEntitiesByIds(eventIds);

        if (!CollectionUtils.isEmpty(events)) {
            List<EventTypeEnum> eventTypes = events.stream()
                    .map(EventEntity::getType)
                    .collect(Collectors.toList());

            checkMbsCompatibility(events, events.size());
            checkIfEventTypesIntercompatible(eventTypes);
            checkAnyEventExpired(events);

            CouponEntity entity = couponRepository.save(CouponEntity.builder()
                    .status(CouponStatusEnum.CREATED)
                    .cost(properties.getCouponCost())
                    .build());

            createSelectionRecords(entity, events);

            CouponDTO response = mapper.map(entity, CouponDTO.class);
            response.setEventIds(couponCreateRequest.getEventIds());

            log.info("createCoupon-end {}", kv("response", response));
            return response;
        } else {
            throw new CouponApiException(ErrorCodeEnum.ENTITY_NOT_FOUND_VALUE, eventIds);
        }

    }

    private void createSelectionRecords(CouponEntity couponEntity, List<EventEntity> events) {
        events.forEach(eventEntity -> {
            CouponSelectionEntity selectionEntity = CouponSelectionEntity.builder()
                    .coupon(couponEntity)
                    .event(eventEntity)
                    .build();

            CouponSelectionEntity couponSelectionEntity = couponSelectionRepository.save(selectionEntity);

            if (CollectionUtils.isEmpty(couponEntity.getSelections())) {
                couponEntity.setSelections(new ArrayList<>());
            }
            couponEntity.getSelections().add(couponSelectionEntity);
        });
    }

    private void checkMbsCompatibility(List<EventEntity> events, int eventCount) {
        events.stream()
                .filter(eventEntity -> eventEntity.getMbs() > eventCount)
                .findAny()
                .ifPresent(eventEntity -> {
                    throw new CouponApiException(ErrorCodeEnum.EVENT_MBS_ERROR,
                            determineExpectedMbs(events), eventCount);
                });
    }

    private Integer determineExpectedMbs(List<EventEntity> events) {
        return events.stream()
                .max(Comparator.comparing(EventEntity::getMbs))
                .map(EventEntity::getMbs)
                .orElse(DEFAULT_MBS);
    }

    /*
    * Take event types which are not intercompatible and pick binary combinations (default) among them.
    * Then check if the given coupon contain any of these combinations.
     */
    private void checkIfEventTypesIntercompatible(List<EventTypeEnum> eventTypes) {
        List<EventTypeEnum> eventTypesToCheck = properties.getEventTypesToCheck();

        List<List<EventTypeEnum>> combinations = Generator.combination(eventTypesToCheck)
                .simple(properties.getEventTypeCombinationCount()).stream()
                .collect(Collectors.toList());

        combinations.forEach(combination -> {
            if (eventTypes.containsAll(combination)) {
                throw new CouponApiException(ErrorCodeEnum.INCOMPATIBLE_EVENT_TYPE, eventTypesToCheck);
            }
        });
    }

    private void checkAnyEventExpired(List<EventEntity> events) {
        events.stream()
                .filter(eventEntity -> LocalDateTime.now().isAfter(eventEntity.getEventDate()))
                .findAny()
                .ifPresent(eventEntity -> {
                    throw new CouponApiException(ErrorCodeEnum.EVENT_EXPIRED, eventEntity.getId());
                });
    }

    @Transactional
    public List<CouponDTO> playCoupons(CouponPlayRequest couponPlayRequest) {
        log.info("playCoupons-begin {}", kv("couponPlayRequest", couponPlayRequest));

        List<Long> couponIds = couponPlayRequest.getCouponIds();
        List<CouponEntity> couponEntityList = couponRepository.findByIdIn(couponIds);

        if (!CollectionUtils.isEmpty(couponEntityList)) {
            checkCouponsAlreadyPurchased(couponEntityList);

            BigDecimal totalCost = calculateTotalCostOfCoupons(couponEntityList);

            balanceService.updateBalance(couponPlayRequest.getUserId(), totalCost, createRandomTransactionId(),
                    TransactionTypeEnum.WITHDRAW);

            List<CouponDTO> response = couponEntityList.stream()
                    .map(couponEntity -> {
                        couponEntity.setUserId(couponPlayRequest.getUserId());
                        couponEntity.setStatus(CouponStatusEnum.PLAYED);
                        couponEntity.setPlayDate(LocalDateTime.now());
                        couponRepository.save(couponEntity);
                        return mapper.map(couponEntity, CouponDTO.class);
                    })
                    .collect(Collectors.toList());
            log.info("playCoupons-end {}", kv("response", response));
            return response;
        } else {
            throw new CouponApiException(ErrorCodeEnum.ENTITY_NOT_FOUND_VALUE, couponIds);
        }

    }

    private String createRandomTransactionId() {
        return UUID.randomUUID().toString();
    }

    private BigDecimal calculateTotalCostOfCoupons(List<CouponEntity> couponEntityList) {
        return couponEntityList.stream()
                .map(CouponEntity::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    private void checkCouponsAlreadyPurchased(List<CouponEntity> coupons) {
        coupons.stream()
                .filter(couponEntity ->
                        couponEntity.getUserId() != null || couponEntity.getStatus() == CouponStatusEnum.PLAYED)
                .findAny()
                .ifPresent(couponEntity -> {
                    throw new CouponApiException(ErrorCodeEnum.COUPON_ALREADY_PURCHASED,
                            couponEntity.getId(), couponEntity.getUserId());
                });
    }

    @Transactional
    public CouponDTO cancelCoupon(Long couponId) {
        log.info("cancelCoupon-begin {}", kv("couponId", couponId));

        CouponEntity entity = couponRepository.findByIdAndStatus(couponId, CouponStatusEnum.PLAYED).orElseThrow(() ->
                new CouponApiException(ErrorCodeEnum.ENTITY_NOT_FOUND_VALUE, couponId));

        checkCouponPlayDate(entity.getPlayDate(), couponId);

        entity.setStatus(CouponStatusEnum.CANCELLED);

        balanceService.updateBalance(entity.getUserId(), entity.getCost(), createRandomTransactionId(),
                TransactionTypeEnum.REFUND);

        couponRepository.save(entity);

        CouponDTO response = mapper.map(entity, CouponDTO.class);
        log.info("cancelCoupon-end {}", kv("response", response));
        return response;
    }

    private void checkCouponPlayDate(LocalDateTime playDate, Long couponId) {
        if (LocalDateTime.now().minus(Duration.ofMinutes(properties.getCouponCancellationAllowedDurationMins()))
                .isAfter(playDate)) {
            throw new CouponApiException(ErrorCodeEnum.CANNOT_CANCEL_COUPON, couponId);
        }
    }

    public List<CouponDTO> getPlayedCoupons(Long userId) {
        log.info("getPlayedCoupons-begin {}", kv("userId", userId));

        List<CouponDTO> response = couponRepository.findByUserIdAndStatus(userId, CouponStatusEnum.PLAYED).stream()
                .map(entity -> mapper.map(entity, CouponDTO.class))
                .collect(Collectors.toList());

        log.info("getPlayedCoupons-end {}", kv("response", response));
        return response;
    }
}
