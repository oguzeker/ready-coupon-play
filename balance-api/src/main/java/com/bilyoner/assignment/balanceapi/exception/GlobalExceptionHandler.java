package com.bilyoner.assignment.balanceapi.exception;

import com.bilyoner.assignment.balanceapi.util.MessageSourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BalanceApiException.class)
    public final ResponseEntity<BalanceApiException> handleBalanceApiException(BalanceApiException ex,
                                                                               WebRequest request) {
        return prepareResponse(ex);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Exception> handleException(
            Exception ex, WebRequest request) {
        return ResponseEntity.status(ErrorCodeEnum.INTERNAL_SERVER_ERROR.getHttpStatus()).body(ex);
    }

    private static ResponseEntity<BalanceApiException> prepareResponse(BalanceApiException exception) {
        BalanceApiException ex = MessageSourceUtil.prepareException(exception);
        log.error("Controller-exception {}", kv("exception", ex));
        return ResponseEntity.status(exception.getErrorCode().getHttpStatus()).body(ex);
    }

}
