package com.bilyoner.assignment.couponapi.exception;

import com.bilyoner.assignment.couponapi.client.exception.BalanceApiException;
import com.bilyoner.assignment.couponapi.client.exception.ErrorCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExternalExceptionHandler implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    public Exception decode(String methodKey, Response response) {
        log.error("----->>>>>>>>>>  Exception response: status : {} reason : {}", response.status(), response.reason());
        return readExternalResponse(response);
    }

    private BalanceApiException readExternalResponse(Response response) {
        try {
            return objectMapper.readValue(response.body().asInputStream(), BalanceApiException.class);
        } catch (IOException e) {
            log.error("Error reading external response {}", kv("response", response));
            return new BalanceApiException(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
    }

}

