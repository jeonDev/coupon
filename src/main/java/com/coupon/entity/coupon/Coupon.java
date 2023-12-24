package com.coupon.entity.coupon;

import com.coupon.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "COUPON")
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COUPON_ID", length = 20)
    private Long couponId;

    @Column(name = "COUPON_NAME", length = 100)
    private String couponName;

    @Column(name = "START_DATE", length = 8)
    private String startDate;

    @Column(name = "END_DATE", length = 8)
    private String endDate;

    @Column(name = "START_TIME", length = 6)
    private String startTime;

    @Column(name = "END_TIME", length = 6)
    private String endTime;

    @Column(name = "USE_YN", length = 1)
    private String useYn;
}
