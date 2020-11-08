package com.bilyoner.assignment.balanceapi.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCodeEnum {

    // BAD_REQUEST - 400 (validation errors, logical errors, etc.)
    BAD_REQUEST(2000, HttpStatus.BAD_REQUEST),
    INSUFFICIENT_BALANCE(2005, HttpStatus.BAD_REQUEST),
    ENTITY_NOT_FOUND_VALUE(2006, HttpStatus.BAD_REQUEST),

    // UNAUTHORIZED - 401 (Authentication problems)
    UNAUTHORIZED(2001, HttpStatus.UNAUTHORIZED),

    // UNPROCESSABLE_ENTITY - 422 (Cannot update record, record already exists, etc.)
    UNPROCESSABLE_ENTITY(2002, HttpStatus.UNPROCESSABLE_ENTITY),

    // INTERNAL_SERVER_ERROR - 500 (Technical problems)
    INTERNAL_SERVER_ERROR(2003, HttpStatus.INTERNAL_SERVER_ERROR),

    // NOT_IMPLEMENTED - 501 (Not supported functionality)
    NOT_IMPLEMENTED(2004, HttpStatus.NOT_IMPLEMENTED);

    @JsonProperty
    private int code;

    @JsonProperty
    private HttpStatus httpStatus;

    @JsonCreator
    public static ErrorCodeEnum fromValue(@JsonProperty("code") int code,
                                          @JsonProperty("httpStatus") HttpStatus httpStatus) {
        for (ErrorCodeEnum c: ErrorCodeEnum.values()) {
            if (c.code == code && c.httpStatus == httpStatus) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(code));
    }

}
