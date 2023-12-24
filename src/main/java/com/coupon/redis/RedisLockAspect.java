package com.coupon.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
@Slf4j
@RequiredArgsConstructor
public class RedisLockAspect {

    private final RedisLockRepository redisLockRepository;

    @Around("@annotation(com.coupon.redis.RedisLockTarget)")
    public Object redisLockAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Redis Proxy 호출!!");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RedisLockTarget redisTarget = signature.getMethod().getAnnotation(RedisLockTarget.class);
        Object result = null;
        try {
            redisLock(redisTarget.value(), redisTarget.delay());
            result = joinPoint.proceed();
        } finally {
            redisLockRepository.unlock(redisTarget.value());
        }
        return result;
    }

    private void redisLock(RedisKeyEnum key, int delayTime) {
        while(!redisLockRepository.lock(key)) {
            try {
                Thread.sleep(delayTime);  // Spin Lock 방식이 Redis에게 주는 부하를 줄여주기 위함.
            } catch (InterruptedException e) {
                log.error("Thread Sleep Exception", e);
            }
        }
    }
}

