package com.laydowncoding.tickitecking.domain.admin.controller;

import com.laydowncoding.tickitecking.domain.admin.dto.request.AdminUserUpdateRequestDto;
import com.laydowncoding.tickitecking.domain.admin.dto.response.AdminReservationResponseDto;
import com.laydowncoding.tickitecking.domain.admin.dto.response.AdminUserResponseDto;
import com.laydowncoding.tickitecking.domain.admin.service.AdminService;
import com.laydowncoding.tickitecking.domain.auditorium.dto.response.AuditoriumResponseDto;
import com.laydowncoding.tickitecking.domain.user.dto.LoginRequestDto;
import com.laydowncoding.tickitecking.global.response.CommonResponse;
import com.laydowncoding.tickitecking.global.util.JwtUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  @GetMapping("/users")
  @Secured({"ROLE_ADMIN"})
  public ResponseEntity<CommonResponse<List<AdminUserResponseDto>>> getUsers() {
    List<AdminUserResponseDto> response = adminService.getUsers();
    return CommonResponse.ok(response);
  }

  @PutMapping("/users/{userId}")
  @Secured({"ROLE_ADMIN"})
  public ResponseEntity<CommonResponse<Void>> updateUser(
      @PathVariable Long userId,
      @RequestBody AdminUserUpdateRequestDto userUpdateRequest
  ) {
    adminService.updateUser(userId, userUpdateRequest);
    return CommonResponse.ok(null);
  }

  @GetMapping("/auditoriums")
  @Secured({"ROLE_ADMIN"})
  public ResponseEntity<CommonResponse<List<AuditoriumResponseDto>>> getAuditoriums() {
    List<AuditoriumResponseDto> response = adminService.getAuditoriums();
    return CommonResponse.ok(response);
  }

  @PatchMapping("/auditoriums/{auditoriumId}/seats/{seatId}")
  @Secured({"ROLE_ADMIN"})
  public ResponseEntity<CommonResponse<Void>> lockSeat(
      @PathVariable Long auditoriumId,
      @PathVariable Long seatId
  ) {
    adminService.lockSeat(auditoriumId, seatId);
    return CommonResponse.ok(null);
  }

  @GetMapping("/reservations")
  @Secured({"ROLE_ADMIN"})
  public ResponseEntity<CommonResponse<List<AdminReservationResponseDto>>> getReservations() {
    List<AdminReservationResponseDto> response = adminService.getReservations();
    return CommonResponse.ok(response);
  }
}
