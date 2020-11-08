package com.bilyoner.assignment.couponapi.service;

import com.bilyoner.assignment.couponapi.model.CouponCreateRequest;
import com.bilyoner.assignment.couponapi.model.CouponDTO;
import com.bilyoner.assignment.couponapi.model.CouponPlayRequest;
import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CouponService {

    Page<CouponDTO> getAllCouponsByCouponStatus(CouponStatusEnum couponStatus, int pageIndex, int pageSize);

    CouponDTO createCoupon(CouponCreateRequest couponCreateRequest);

    List<CouponDTO> playCoupons(CouponPlayRequest couponPlayRequest);

    CouponDTO cancelCoupon(Long couponId);

    List<CouponDTO> getPlayedCoupons(Long userId);

}
