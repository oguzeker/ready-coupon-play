package com.bilyoner.assignment.couponapi.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCodeEnum {

    // BAD_REQUEST - 400 (validation errors, logical errors, etc.)
    BAD_REQUEST(1013, HttpStatus.BAD_REQUEST),
    EVENT_EXPIRED(1003, HttpStatus.BAD_REQUEST),
    INCOMPATIBLE_EVENT_TYPE(1004, HttpStatus.BAD_REQUEST),
    EVENT_MBS_ERROR(1005, HttpStatus.BAD_REQUEST),
    COUPON_ALREADY_PURCHASED(1006, HttpStatus.BAD_REQUEST),
    CANNOT_CANCEL_COUPON(1007, HttpStatus.BAD_REQUEST),
    ENTITY_NOT_FOUND_VALUE(1002, HttpStatus.BAD_REQUEST),
    METHOD_ARGUMENT_TYPE_MISMATCH(1009, HttpStatus.BAD_REQUEST),

    // UNAUTHORIZED - 401 (Authentication problems)
    UNAUTHORIZED(1014, HttpStatus.UNAUTHORIZED),

    // UNPROCESSABLE_ENTITY - 422 (Cannot update record, record already exists, etc.)
    UNPROCESSABLE_ENTITY(1015, HttpStatus.UNPROCESSABLE_ENTITY),
    ENTITY_NOT_FOUND_NO_VALUE(1010, HttpStatus.UNPROCESSABLE_ENTITY),
    ENTITY_ALREADY_EXISTS(1011, HttpStatus.UNPROCESSABLE_ENTITY),
    CAPACITY_FULL(1012, HttpStatus.UNPROCESSABLE_ENTITY),

    // INTERNAL_SERVER_ERROR - 500 (Technical problems)
    INTERNAL_SERVER_ERROR(1000, HttpStatus.INTERNAL_SERVER_ERROR),

    // NOT_IMPLEMENTED - 501 (Not supported functionality)
    NOT_IMPLEMENTED(1016, HttpStatus.NOT_IMPLEMENTED);

    private int code;
    private HttpStatus httpStatus;
}
