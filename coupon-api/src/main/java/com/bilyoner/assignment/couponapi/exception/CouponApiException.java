package com.bilyoner.assignment.couponapi.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CouponApiException extends RuntimeException {

    private final ErrorCodeEnum errorCode;
    private final Object[] args;
    private String message;

    public CouponApiException(ErrorCodeEnum errorCode, Object... args) {
        super();
        this.errorCode = errorCode;
        this.args = args;
    }

}
