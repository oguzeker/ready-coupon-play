package com.bilyoner.assignment.balanceapi.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.function.Consumer;

@Slf4j
@UtilityClass
public final class RedissonUtil {

    private static final String USER_BALANCES_LOCK = "user-balances-lock";

    public static <T> void executeWithFairLock(RedissonClient redissonClient, Consumer<T> method, T input) {
        RLock lock = redissonClient.getFairLock(USER_BALANCES_LOCK);

        lock.lock();
        try {
            method.accept(input);
        } finally {
            lock.unlock();
        }
    }

}
