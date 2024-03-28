package com.laydowncoding.tickitecking.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    @Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*\\W)(?=.*\\d).{8,15}$",
        message = "비밀번호는 최소 8자 이상, 15자 이하로 알파벳과 특수문자, 숫자로 구성되어야 합니다."
    )
    private String password;

    @Email(message = "이메일 형식을 지켜주세요")
    private String email;

    private String nickname;
}
