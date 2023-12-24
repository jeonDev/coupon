package com.coupon.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisLockRepository {
    private final RedisTemplate<String, String> redisTemplate;
    private static final Long TIMEOUT_MILLIS = 3000L;

    public Boolean lock(RedisKeyEnum key) {
        return redisTemplate
                .opsForValue()
                .setIfAbsent(generateKey(key.getKey()), "lock"
                        , Duration.ofMillis(TIMEOUT_MILLIS)
                );
    }

    public Boolean unlock(RedisKeyEnum key) {
        return redisTemplate.delete(generateKey(key.getKey()));
    }

    private String generateKey(Long key) {
        return key.toString();
    }
}
