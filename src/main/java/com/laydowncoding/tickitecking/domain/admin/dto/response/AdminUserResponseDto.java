package com.laydowncoding.tickitecking.domain.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserResponseDto {

    private Long userId;
    private String username;
    private String email;
    private String nickname;
    private String role;
}
