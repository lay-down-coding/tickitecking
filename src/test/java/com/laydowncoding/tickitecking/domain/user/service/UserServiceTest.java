package com.laydowncoding.tickitecking.domain.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.*;

import com.laydowncoding.tickitecking.domain.user.dto.SignupRequestDto;
import com.laydowncoding.tickitecking.domain.user.entity.User;
import com.laydowncoding.tickitecking.domain.user.repository.UserRepository;
import com.laydowncoding.tickitecking.global.exception.CustomRuntimeException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @DisplayName("회원 가입 요청 성공")
    @Test
    void signup_success() {
        //given
        given(userRepository.findByUsername(any(String.class))).willReturn(Optional.empty());
        given(userRepository.findByEmail(any(String.class))).willReturn(Optional.empty());
        given(userRepository.findByNickname(any(String.class))).willReturn(Optional.empty());
        SignupRequestDto requestDto = SignupRequestDto.builder()
            .username("test_username")
            .password("pass@i344")
            .email("test@email.com")
            .nickname("johny")
            .build();

        //when & then
        assertDoesNotThrow(() -> userService.signup(requestDto));
    }

    @DisplayName("회원 가입 요청 실패")
    @Test
    void signup_fail() {
        //given
        given(userRepository.findByUsername(any(String.class))).willReturn(
            Optional.ofNullable(User.builder()
                .id(1L)
                .build()));

        //when
        SignupRequestDto requestDto = SignupRequestDto.builder()
            .username("test_username")
            .password("pass@i344")
            .email("test@email.com")
            .nickname("johny")
            .build();

        //when && then
        assertThatThrownBy(() -> userService.signup(requestDto))
            .isInstanceOf(CustomRuntimeException.class);
    }
}
