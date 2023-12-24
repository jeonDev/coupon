package com.coupon.vo;

import com.coupon.entity.coupon.Coupon;
import lombok.Data;

@Data
public class CouponCreateDto {

    private Long couponId;
    private String couponName;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private String useYn;

    public Coupon dtoToEntity() {
        return Coupon.builder()
                .couponId(couponId)
                .couponName(couponName)
                .startDate(startDate)
                .endDate(endDate)
                .startTime(startTime)
                .endTime(endTime)
                .useYn(useYn)
                .build();
    }
}
