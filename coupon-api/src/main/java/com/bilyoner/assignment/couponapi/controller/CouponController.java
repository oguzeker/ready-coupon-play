package com.bilyoner.assignment.couponapi.controller;

import com.bilyoner.assignment.couponapi.configuration.SwaggerConfiguration;
import com.bilyoner.assignment.couponapi.model.CouponCreateRequest;
import com.bilyoner.assignment.couponapi.model.CouponDTO;
import com.bilyoner.assignment.couponapi.model.CouponPlayRequest;
import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {
        SwaggerConfiguration.TAG_COUPON
})
public interface CouponController {

    @ApiOperation(value = "Get All Coupons By Status", notes = "This endpoint retrieves coupons by their status")
    ResponseEntity<Page<CouponDTO>> getAllCouponsByCouponStatus(
            @ApiParam(value = "Status of the coupon entity", example = "CREATED")
            @RequestParam CouponStatusEnum couponStatus,
            @ApiParam(value = "Index of the requested page of bulk results", example = "0") @RequestParam int pageIndex,
            @ApiParam(value = "Size of the requested page of bulk results", example = "10") @RequestParam int pageSize);

    @ApiOperation(value = "Create Coupon", notes = "This endpoint creates coupon entity")
    ResponseEntity<CouponDTO> createCoupon(@ApiParam(value = "Details of the coupon entity to create")
                                           @RequestBody @Valid CouponCreateRequest couponCreateRequest);

    @ApiOperation(value = "Get All Played Coupons Of User", notes = "This endpoint retrieves coupons belonging " +
            "to a given user")
    ResponseEntity<List<CouponDTO>> getPlayedCoupons(@ApiParam(value = "Id of the user entity", example = "1")
                                                     @PathVariable Long userId);

    @ApiOperation(value = "Play Coupon", notes = "This endpoint assigns the given coupon to a user and " +
            "deducts coupon cost from the balance of the user")
    ResponseEntity<List<CouponDTO>> playCoupons(@ApiParam(value = "Coupon entity to play")
                                                @Valid @RequestBody CouponPlayRequest couponPlayRequest);

    @ApiOperation(value = "Cancel Coupon", notes = "This endpoint cancels a played coupon and performs a refund " +
            "to the user")
    ResponseEntity<CouponDTO> cancelCoupon(@ApiParam(value = "Id of the coupon entity", example = "1")
                                           @PathVariable Long couponId);

}
