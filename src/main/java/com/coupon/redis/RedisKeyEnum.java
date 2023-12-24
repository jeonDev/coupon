package com.coupon.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisKeyEnum {

    COUPON_STOCK(1L);

    private final Long key;
}
