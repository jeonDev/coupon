package com.coupon.service;

import com.coupon.entity.user.User;
import com.coupon.vo.CreateUserDto;

public interface UserService {
    void createUser(CreateUserDto dto);
    User findByUser(String id);
}
