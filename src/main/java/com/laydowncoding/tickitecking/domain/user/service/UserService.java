package com.laydowncoding.tickitecking.domain.user.service;

import com.laydowncoding.tickitecking.domain.user.dto.SignupRequestDto;
import com.laydowncoding.tickitecking.domain.user.dto.UserReservationResponseDto;
import com.laydowncoding.tickitecking.domain.user.dto.UserResponseDto;
import com.laydowncoding.tickitecking.domain.user.dto.UserUpdateRequestDto;
import java.util.List;

public interface UserService {

    void signup(SignupRequestDto requestDto);

    void updateUser(Long userId, UserUpdateRequestDto requestDto);

    UserResponseDto getUser(Long userId);

    void deleteUser(Long userId);

    List<UserReservationResponseDto> getReservations(Long id);
}
