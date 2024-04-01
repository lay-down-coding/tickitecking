package com.laydowncoding.tickitecking.domain.admin.service;

import com.laydowncoding.tickitecking.domain.admin.dto.response.AllUserResponseDto;
import com.laydowncoding.tickitecking.domain.user.dto.LoginRequestDto;
import java.util.List;

public interface AdminService {

  String login(LoginRequestDto loginRequest);

  List<AllUserResponseDto> getUsers();
}
