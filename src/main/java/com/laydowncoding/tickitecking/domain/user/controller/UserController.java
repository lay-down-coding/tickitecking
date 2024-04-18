package com.laydowncoding.tickitecking.domain.user.controller;

import com.laydowncoding.tickitecking.domain.user.dto.SignupRequestDto;
import com.laydowncoding.tickitecking.domain.user.dto.UserReservationResponseDto;
import com.laydowncoding.tickitecking.domain.user.dto.UserResponseDto;
import com.laydowncoding.tickitecking.domain.user.dto.UserUpdateRequestDto;
import com.laydowncoding.tickitecking.domain.user.service.UserService;
import com.laydowncoding.tickitecking.global.response.CommonResponse;
import com.laydowncoding.tickitecking.global.service.RedisService;
import com.laydowncoding.tickitecking.global.service.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final RedisService redisService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<Void>> signup(
        @Valid @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return CommonResponse.ok(null);
    }

    @PutMapping("/my")
    public ResponseEntity<CommonResponse<Void>> updateUser(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody UserUpdateRequestDto requestDto) {
        userService.updateUser(userDetails.getUser().getId(), requestDto);
        return CommonResponse.ok(null);
    }

    @GetMapping("/my")
    public ResponseEntity<CommonResponse<UserResponseDto>> getUser(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponseDto responseDto = userService.getUser(userDetails.getUser().getId());
        return CommonResponse.ok(responseDto);
    }

    @DeleteMapping("/my")
    public ResponseEntity<CommonResponse<Void>> deleteUser(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.deleteUser(userDetails.getUser().getId());
        return CommonResponse.ok(null);
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<Void>> logout(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        redisService.deleteValues(userDetails.getUser().getUsername());
        return CommonResponse.ok(null);
    }

    @GetMapping("/my/reservations")
    public ResponseEntity<CommonResponse<List<UserReservationResponseDto>>> getReservations(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<UserReservationResponseDto> response = userService.getReservations(
            userDetails.getUser().getId());
        return CommonResponse.ok(response);
    }
}
