package com.coupon.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "USER")
public class User {

    @Id
    @Column(name = "ID", length = 20)
    private String id;

    @Column(name = "NAME", length = 20)
    private String name;
}
