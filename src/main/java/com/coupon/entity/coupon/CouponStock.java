package com.coupon.entity.coupon;

import com.coupon.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "COUPON_STOCK")
public class CouponStock extends BaseEntity {

    @Id
    @Column(name = "COUPON_ID")
    private Long couponId;

    @Column(name = "STOCK", length = 10)
    private Long stock;

    public CouponStock updateStock(Long stock) {
        this.stock += stock;
        return this;
    }

    public CouponStockHistory entityHistoryGenerator(Long stock) {
        return CouponStockHistory.builder()
                .couponId(couponId)
                .stock(stock)
                .build();
    }
}
