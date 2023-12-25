package com.coupon.redis;

import io.lettuce.core.RedisException;
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
public class RedisLettuceLockAspect {
    private final static int MAX_RETRY_COUNT = 10;
    private final RedisLettuceLockRepository redisLockRepository;

    @Around("@annotation(com.coupon.redis.RedisLettuceLockTarget)")
    public Object redisLockAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Redis Proxy 호출!!");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RedisLettuceLockTarget redisTarget = signature.getMethod().getAnnotation(RedisLettuceLockTarget.class);
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
        int retryCnt = 0;
        while(!redisLockRepository.lock(key)) {
            if(retryCnt >= MAX_RETRY_COUNT) throw new RedisException("요청 처리 불가");
            retryCnt++;
            try {
                Thread.sleep(delayTime);  // Spin Lock 방식이 Redis에게 주는 부하를 줄여주기 위함.
            } catch (InterruptedException e) {
                log.error("Thread Sleep Exception", e);
            }
        }
    }
}

