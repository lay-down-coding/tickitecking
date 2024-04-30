package com.laydowncoding.tickitecking.domain.admin.service;

import com.laydowncoding.tickitecking.domain.admin.dto.request.AdminLockSeatRequestDto;
import com.laydowncoding.tickitecking.domain.admin.dto.request.AdminUserUpdateRequestDto;
import com.laydowncoding.tickitecking.domain.admin.dto.response.AdminReservationResponseDto;
import com.laydowncoding.tickitecking.domain.admin.dto.response.AdminUserResponseDto;
import com.laydowncoding.tickitecking.domain.auditorium.dto.response.AuditoriumResponseDto;
import com.laydowncoding.tickitecking.domain.user.dto.LoginRequestDto;
import java.util.List;

public interface AdminService {

    String login(LoginRequestDto loginRequest);

    List<AdminUserResponseDto> getUsers();

    void updateUser(Long userId, AdminUserUpdateRequestDto userUpdateRequest);

    List<AuditoriumResponseDto> getAuditoriums();

    void lockSeat(Long auditoriumId, AdminLockSeatRequestDto requestDto);

    List<AdminReservationResponseDto> getReservations();
}
