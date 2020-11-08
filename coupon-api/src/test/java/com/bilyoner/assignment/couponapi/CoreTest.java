package com.bilyoner.assignment.couponapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Tag("core")
@DisplayName("Core Tests")
@ExtendWith(MockitoExtension.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface CoreTest {
}
