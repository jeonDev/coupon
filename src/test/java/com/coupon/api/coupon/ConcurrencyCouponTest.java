package com.coupon.api.coupon;

import com.coupon.service.CouponService;
import com.coupon.service.UserService;
import com.coupon.vo.CouponIssueDto;
import com.coupon.vo.CreateUserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class ConcurrencyCouponTest {

    final int COUPON_THREAD_COUNT = 120;

    @Autowired
    private CouponService couponService;

    @Autowired
    private UserService userService;

    @Test
    void 쿠폰발급_100장() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(COUPON_THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(COUPON_THREAD_COUNT);

//        for(int i = 0; i < COUPON_THREAD_COUNT; i++) {
//            CreateUserDto dto = new CreateUserDto();
//            dto.setId("test" + i);
//            dto.setName("테스트" + i);
//            executorService.execute(() -> {
//                userService.createUser(dto);
//                latch.countDown();
//            });
//        }
        for(int i = 0; i < COUPON_THREAD_COUNT; i++) {
            CouponIssueDto dto = new CouponIssueDto();
            dto.setUserId("test" + i);
            dto.setCouponId(1L);
            executorService.execute(() -> {
                couponService.couponIssue(dto);
                latch.countDown();
            });
        }

        latch.await();

    }
}
