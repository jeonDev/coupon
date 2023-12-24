package com.coupon.vo;

import com.coupon.entity.user.User;
import lombok.Data;

@Data
public class CreateUserDto {
    private String id;
    private String name;

    public User dtoToEntity() {
        return User.builder()
                .id(id)
                .name(name)
                .build();
    }
}
