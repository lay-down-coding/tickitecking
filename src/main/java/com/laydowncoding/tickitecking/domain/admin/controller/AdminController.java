package com.laydowncoding.tickitecking.domain.admin.controller;

import com.laydowncoding.tickitecking.domain.admin.service.AdminService;
import com.laydowncoding.tickitecking.domain.user.dto.LoginRequestDto;
import com.laydowncoding.tickitecking.global.response.CommonResponse;
import com.laydowncoding.tickitecking.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

  private final AdminService adminService;

  @PostMapping("/login")
  public ResponseEntity<CommonResponse<Void>> login(
      @RequestBody LoginRequestDto loginRequest
  ) {
    String accessToken = adminService.login(loginRequest);


    HttpHeaders headers = new HttpHeaders();
    headers.add(JwtUtil.AUTHORIZATION_HEADER, accessToken);
    return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
  }
}
