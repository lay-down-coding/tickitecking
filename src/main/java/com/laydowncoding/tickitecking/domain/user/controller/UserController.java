package com.laydowncoding.tickitecking.domain.user.controller;

import com.laydowncoding.tickitecking.domain.user.dto.SignupRequestDto;
import com.laydowncoding.tickitecking.domain.user.dto.UserUpdateRequestDto;
import com.laydowncoding.tickitecking.domain.user.service.UserService;
import com.laydowncoding.tickitecking.global.response.CommonResponse;
import com.laydowncoding.tickitecking.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<Void>> signup(@Valid @RequestBody SignupRequestDto requestDto) {
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
}
