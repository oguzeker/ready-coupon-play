package com.bilyoner.assignment.couponapi.controller.impl;

import com.bilyoner.assignment.couponapi.controller.CouponController;
import com.bilyoner.assignment.couponapi.model.CouponCreateRequest;
import com.bilyoner.assignment.couponapi.model.CouponDTO;
import com.bilyoner.assignment.couponapi.model.CouponPlayRequest;
import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import com.bilyoner.assignment.couponapi.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/coupons")
public class CouponControllerImpl implements CouponController {

    private final CouponService couponService;

    @GetMapping
    public ResponseEntity<Page<CouponDTO>> getAllCouponsByCouponStatus(@RequestParam CouponStatusEnum couponStatus,
                                                                       @RequestParam int pageIndex,
                                                                       @RequestParam int pageSize) {
        return ResponseEntity.ok(couponService.getAllCouponsByCouponStatus(couponStatus, pageIndex, pageSize));
    }

    @PostMapping
    public ResponseEntity<CouponDTO> createCoupon(@RequestBody @Valid CouponCreateRequest couponCreateRequest) {
        return ResponseEntity.ok(couponService.createCoupon(couponCreateRequest));
    }

    @GetMapping("{userId}")
    public ResponseEntity<List<CouponDTO>> getPlayedCoupons(@PathVariable Long userId) {
        return ResponseEntity.ok(couponService.getPlayedCoupons(userId));
    }

    @PostMapping("play")
    public ResponseEntity<List<CouponDTO>> playCoupons(@Valid @RequestBody CouponPlayRequest couponPlayRequest) {
        return ResponseEntity.ok(couponService.playCoupons(couponPlayRequest));
    }

    @PutMapping("{couponId}")
    public ResponseEntity<CouponDTO> cancelCoupon(@PathVariable Long couponId) {
        return ResponseEntity.ok(couponService.cancelCoupon(couponId));
    }
}