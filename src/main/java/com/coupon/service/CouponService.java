package com.coupon.service;

import com.coupon.vo.CouponCreateDto;
import com.coupon.vo.CouponIssueDto;
import com.coupon.vo.CouponStockAdjustmentsDto;

public interface CouponService {
    // 쿠폰 정보 생성
    void couponCreate(CouponCreateDto dto);

    // 쿠폰 재고 조정
    void couponStockAdjustments(CouponStockAdjustmentsDto dto);

    // 쿠폰 발급 요청
    void couponIssue(CouponIssueDto dto);

}
