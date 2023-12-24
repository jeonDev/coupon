package com.coupon.api.coupon;

import com.coupon.entity.user.User;
import com.coupon.entity.user.UserRepository;
import com.coupon.service.CouponService;
import com.coupon.vo.CouponCreateDto;
import com.coupon.vo.CouponIssueDto;
import com.coupon.vo.CouponStockAdjustmentsDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CouponTest {

    @Autowired
    private CouponService couponBasicServiceImpl;

    @Autowired
    private CouponService couponRedisServiceImpl;
    @Autowired
    private CouponService couponRedisProxyServiceImpl;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 쿠폰정보생성_Basic() {
        CouponCreateDto dto = new CouponCreateDto();
        dto.setCouponName("테스트1");
        dto.setStartDate("20231201");
        dto.setEndDate("20231231");
        dto.setStartTime("000000");
        dto.setEndTime("235959");
        dto.setUseYn("Y");

        couponBasicServiceImpl.couponCreate(dto);
    }

    @Test
    void 쿠폰재고등록_Basic() {
        CouponStockAdjustmentsDto dto = new CouponStockAdjustmentsDto();
        dto.setCouponId(1L);
        dto.setStock(100L);
        couponBasicServiceImpl.couponStockAdjustments(dto);
    }

    @Test
    void 사용자쿠폰발급_Basic() {
        User user = User.builder()
                .id("test")
                .name("테스트")
                .build();
        userRepository.save(user);

        CouponIssueDto dto = new CouponIssueDto();
        dto.setUserId("test");
        dto.setCouponId(1L);
        couponBasicServiceImpl.couponIssue(dto);
    }

    @Test
    void 쿠폰정보생성_Redis() {
        CouponCreateDto dto = new CouponCreateDto();
        dto.setCouponName("테스트2");
        dto.setStartDate("20231201");
        dto.setEndDate("20231231");
        dto.setStartTime("000000");
        dto.setEndTime("235959");
        dto.setUseYn("Y");

        couponRedisServiceImpl.couponCreate(dto);
    }

    @Test
    void 쿠폰재고등록_Redis() {
        CouponStockAdjustmentsDto dto = new CouponStockAdjustmentsDto();
        dto.setCouponId(2L);
        dto.setStock(100L);
        couponRedisServiceImpl.couponStockAdjustments(dto);
    }

    @Test
    void 쿠폰정보생성_RedisProxy() {
        CouponCreateDto dto = new CouponCreateDto();
        dto.setCouponName("테스트3");
        dto.setStartDate("20231201");
        dto.setEndDate("20231231");
        dto.setStartTime("000000");
        dto.setEndTime("235959");
        dto.setUseYn("Y");

        couponRedisProxyServiceImpl.couponCreate(dto);
    }

    @Test
    void 쿠폰재고등록_RedisProxy() {
        CouponStockAdjustmentsDto dto = new CouponStockAdjustmentsDto();
        dto.setCouponId(3L);
        dto.setStock(100L);
        couponRedisProxyServiceImpl.couponStockAdjustments(dto);
    }
}
