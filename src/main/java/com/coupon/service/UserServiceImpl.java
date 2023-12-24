package com.coupon.service;

import com.coupon.entity.user.User;
import com.coupon.entity.user.UserRepository;
import com.coupon.vo.CreateUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void createUser(CreateUserDto dto) {
        userRepository.save(dto.dtoToEntity());
    }

    @Override
    public User findByUser(String id) {
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
