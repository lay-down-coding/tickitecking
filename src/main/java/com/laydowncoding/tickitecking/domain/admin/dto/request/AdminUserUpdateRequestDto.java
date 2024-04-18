package com.laydowncoding.tickitecking.domain.admin.dto.request;

import com.laydowncoding.tickitecking.domain.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserUpdateRequestDto {

    private String username;
    private String password;
    private String email;
    private String nickname;
    private UserRole role;
}
