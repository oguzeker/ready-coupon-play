package com.bilyoner.assignment.couponapi.exception;

import com.bilyoner.assignment.couponapi.util.MessageSourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.NoSuchElementException;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CouponApiException.class)
    public final ResponseEntity<CouponApiException> handleCouponApiException(CouponApiException ex,
                                                                             WebRequest request) {
        return prepareResponse(ex);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<CouponApiException> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex, WebRequest request) {

        MethodParameter parameter = ex.getParameter();
        String parameterType = parameter.getParameterType().getName();
        String parameterName = ex.getName();

        CouponApiException exception = new CouponApiException(
                ErrorCodeEnum.METHOD_ARGUMENT_TYPE_MISMATCH, ex.getRootCause(), parameterType, parameterName);

        return prepareResponse(exception);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<CouponApiException> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex, WebRequest request) {
        return prepareResponse(new CouponApiException(ErrorCodeEnum.ENTITY_ALREADY_EXISTS, ex));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public final ResponseEntity<CouponApiException> handleNoSuchElementException(
            NoSuchElementException ex, WebRequest request) {

        return prepareResponse(new CouponApiException(ErrorCodeEnum.ENTITY_NOT_FOUND_NO_VALUE, ex,
                ex.getMessage()));
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public final ResponseEntity<CouponApiException> handleEmptyResultDataAccessException(
            EmptyResultDataAccessException ex, WebRequest request) {
        return prepareResponse(new CouponApiException(ErrorCodeEnum.ENTITY_NOT_FOUND_NO_VALUE,
                ex.getRootCause(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Exception> handleException(
            Exception ex, WebRequest request) {
        return ResponseEntity.status(ErrorCodeEnum.INTERNAL_SERVER_ERROR.getHttpStatus()).body(ex);
    }

    private static ResponseEntity<CouponApiException> prepareResponse(CouponApiException exception) {
        CouponApiException ex = MessageSourceUtil.prepareException(exception);
        log.error("Controller-exception {}", kv("exception", ex));
        return ResponseEntity.status(exception.getErrorCode().getHttpStatus()).body(ex);
    }

}