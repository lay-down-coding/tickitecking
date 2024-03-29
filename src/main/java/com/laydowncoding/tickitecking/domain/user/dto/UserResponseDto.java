package com.laydowncoding.tickitecking.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private String username;
    private String email;
    private String nickname;
}
