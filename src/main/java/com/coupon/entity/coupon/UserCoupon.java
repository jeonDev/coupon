package com.coupon.entity.coupon;

import com.coupon.entity.BaseEntity;
import com.coupon.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "USER_COUPON")
public class UserCoupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_COUPON_SEQ")
    private Long userCouponSeq;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "COUPON_ID")
    private Coupon coupon;

    @Column(name = "USE_STATE_YN")
    private String useStateYn;



}
