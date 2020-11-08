package com.bilyoner.assignment.couponapi.client.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BalanceApiException extends RuntimeException {

    private ErrorCodeEnum errorCode;
    private Object[] args;
    private String message;

    public BalanceApiException(ErrorCodeEnum errorCode, Object... args) {
        super();
        this.errorCode = errorCode;
        this.args = args;
    }

}
