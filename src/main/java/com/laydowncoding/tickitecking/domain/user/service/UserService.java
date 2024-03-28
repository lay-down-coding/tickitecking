package com.laydowncoding.tickitecking.domain.user.service;

import com.laydowncoding.tickitecking.domain.user.dto.SignupRequestDto;

public interface UserService {

    void signup(SignupRequestDto requestDto);
}
