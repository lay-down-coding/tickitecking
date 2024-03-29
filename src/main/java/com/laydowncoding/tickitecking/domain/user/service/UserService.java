package com.laydowncoding.tickitecking.domain.user.service;

import com.laydowncoding.tickitecking.domain.user.dto.SignupRequestDto;
import com.laydowncoding.tickitecking.domain.user.dto.UserUpdateRequestDto;

public interface UserService {

    void signup(SignupRequestDto requestDto);

    void updateUser(Long userId, UserUpdateRequestDto requestDto);
}
