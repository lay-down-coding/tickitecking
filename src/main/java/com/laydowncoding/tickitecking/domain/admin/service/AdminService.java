package com.laydowncoding.tickitecking.domain.admin.service;

import com.laydowncoding.tickitecking.domain.user.dto.LoginRequestDto;

public interface AdminService {

  String login(LoginRequestDto loginRequest);
}
