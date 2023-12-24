package com.coupon.service;

import com.coupon.entity.coupon.*;
import com.coupon.entity.user.User;
import com.coupon.redis.RedisKeyEnum;
import com.coupon.redis.RedisLettuceLockRepository;
import com.coupon.redis.RedisLettuceLockTarget;
import com.coupon.vo.CouponCreateDto;
import com.coupon.vo.CouponIssueDto;
import com.coupon.vo.CouponStockAdjustmentsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponRedisLettuceProxyServiceImpl implements CouponService {

    private static final Long COUPON_ISSUE_CNT = -1L;
    private static final Long MAX_COUPON_CNT = 0L;

    private final CouponRepository couponRepository;
    private final CouponStockRepository couponStockRepository;
    private final CouponStockHistoryRepository couponStockHistoryRepository;
    private final UserCouponRepository userCouponRepository;
    private final UserService userService;
    private final RedisLettuceLockRepository redisLockRepository;

    @Override
    public void couponCreate(CouponCreateDto dto) {
        Coupon coupon = dto.dtoToEntity();
        couponRepository.save(coupon);
    }

    @Override
    @Transactional
    @RedisLettuceLockTarget(value = RedisKeyEnum.COUPON_STOCK_LETTUCE, delay = 100)
    public void couponStockAdjustments(CouponStockAdjustmentsDto dto) {
        // 1. Coupon 존재 여부 체크
        Coupon coupon = couponRepository.findById(dto.getCouponId()).orElseThrow(IllegalArgumentException::new);

        // 2. Coupon Stock 존재여부 체크
        couponStockRepository.findById(coupon.getCouponId()).ifPresentOrElse(
                // 2-1. Coupon Stock 존재 -> 재고 조정
                item -> {
                    CouponStock couponStock = item.updateStock(dto.getStock());
                    this.couponStockChange(couponStock, dto.getStock());
                },
                // 2-2. Coupon Stock 존재 하지 않음 -> 재고 수량 만큼 등록
                () -> {
                    CouponStock couponStock = CouponStock.builder()
                            .couponId(coupon.getCouponId())
                            .stock(dto.getStock())
                            .build();
                    this.couponStockChange(couponStock, dto.getStock());
                });
    }

    @Override
    @Transactional
    @RedisLettuceLockTarget(value = RedisKeyEnum.COUPON_STOCK_LETTUCE, delay = 100)
    public void couponIssue(CouponIssueDto dto) {
        // 1. User 존재 여부 체크
        User user = userService.findByUser(dto.getUserId());

        // 2. Coupon 존재 여부 체크
        Coupon coupon = couponRepository.findById(dto.getCouponId()).orElseThrow(IllegalArgumentException::new);

        // 3. Coupon 재고 수량 체크
        CouponStock couponStock = couponStockRepository.findById(coupon.getCouponId()).orElseThrow(() -> new IllegalStateException("재고 수량 없음"));

        if(couponStock.getStock() <= MAX_COUPON_CNT) throw new IllegalStateException("재고 수량 부족");

        // 4. 재고 수량 차감
        couponStock.updateStock(COUPON_ISSUE_CNT);
        couponStockChange(couponStock, COUPON_ISSUE_CNT);
        log.info(couponStock.getStock().toString());

        // 5. 고객 쿠폰 추가
        UserCoupon userCoupon = UserCoupon.builder()
                .user(user)
                .coupon(coupon)
                .useStateYn("Y")
                .build();
        userCouponRepository.save(userCoupon);
    }

    private void couponStockChange(CouponStock couponStock, Long stock) {
        couponStockRepository.save(couponStock);
        couponStockHistoryRepository.save(couponStock.entityHistoryGenerator(stock));
    }
}
