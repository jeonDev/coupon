package com.coupon.api.user;

import com.coupon.service.UserService;
import com.coupon.vo.CreateUserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {
    @Autowired
    UserService userService;

    @Test
    void 사용자생성() {
        CreateUserDto dto = new CreateUserDto();
        dto.setId("test1");
        dto.setName("테스트1");
        userService.createUser(dto);
    }
}
