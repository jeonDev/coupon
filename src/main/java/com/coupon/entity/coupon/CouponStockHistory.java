package com.coupon.entity.coupon;

import com.coupon.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "COUPON_STOCK_HISTORY")
public class CouponStockHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COUPON_STOCK_HISTORY_SEQ")
    private Long id;

    @Column(name = "COUPON_ID")
    private Long couponId;

    @Column(name = "STOCK", length = 10)
    private Long stock;
}
