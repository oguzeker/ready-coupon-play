package com.bilyoner.assignment.couponapi;

import com.bilyoner.assignment.couponapi.entity.CouponEntity;
import com.bilyoner.assignment.couponapi.entity.CouponSelectionEntity;
import com.bilyoner.assignment.couponapi.entity.EventEntity;
import com.bilyoner.assignment.couponapi.model.CouponCreateRequest;
import com.bilyoner.assignment.couponapi.model.CouponDTO;
import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import com.bilyoner.assignment.couponapi.model.enums.EventTypeEnum;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;

public class TestBase {

    protected static final long ID = 1L;
    protected static final int MBS = 0;
    protected static final int DAYS = 30;
    protected static final LocalDateTime EVENT_DATE = LocalDateTime.now().plus(Duration.ofDays(DAYS));
    protected static final LocalDateTime AUDIT_DATE = LocalDateTime.now();
    protected static final int COST_VAL = 5;
    protected static final BigDecimal COST = BigDecimal.valueOf(COST_VAL);
    protected static final String EVENT_NAME = "Barcelona-Arsenal";
    protected static final int EVENT_TYPE_COMBINATION_COUNT = 1;

    public CouponCreateRequest createCouponCreateRequest() {
        return CouponCreateRequest.builder()
                .eventIds(Collections.singletonList(ID))
                .build();
    }

    public EventEntity createEventEntity() {
        return EventEntity.builder()
                .mbs(MBS)
                .type(EventTypeEnum.FOOTBALL)
                .eventDate(EVENT_DATE)
                .name(EVENT_NAME)
                .id(ID)
                .build();
    }

    public CouponEntity createCouponEntity() {
        return CouponEntity.builder()
                .id(ID)
                .cost(COST)
                .status(CouponStatusEnum.CREATED)
                .createDate(AUDIT_DATE)
                .updateDate(AUDIT_DATE)
                .build();
    }

    public CouponSelectionEntity createCouponSelectionEntity(EventEntity eventEntity, CouponEntity couponEntity) {
        return CouponSelectionEntity.builder()
                .event(eventEntity)
                .coupon(couponEntity)
                .createDate(AUDIT_DATE)
                .id(ID)
                .build();
    }

    public CouponDTO createCouponDTO() {
        return CouponDTO.builder()
                .id(ID)
                .cost(COST)
                .status(CouponStatusEnum.CREATED)
                .createDate(AUDIT_DATE)
                .updateDate(AUDIT_DATE)
                .build();
    }

}
