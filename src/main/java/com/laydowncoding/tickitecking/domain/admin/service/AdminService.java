package com.laydowncoding.tickitecking.domain.admin.service;

import com.laydowncoding.tickitecking.domain.admin.dto.request.AdminUserUpdateRequestDto;
import com.laydowncoding.tickitecking.domain.admin.dto.response.AdminUserResponseDto;
import com.laydowncoding.tickitecking.domain.user.dto.LoginRequestDto;
import java.util.List;

public interface AdminService {

  String login(LoginRequestDto loginRequest);

  List<AdminUserResponseDto> getUsers();

  void updateUser(Long userId, AdminUserUpdateRequestDto userUpdateRequest);
}
