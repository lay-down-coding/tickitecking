package com.laydowncoding.tickitecking.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;

import com.laydowncoding.tickitecking.domain.reservations.repository.ReservationRepository;
import com.laydowncoding.tickitecking.domain.user.dto.SignupRequestDto;
import com.laydowncoding.tickitecking.domain.user.dto.UserReservationResponseDto;
import com.laydowncoding.tickitecking.domain.user.dto.UserResponseDto;
import com.laydowncoding.tickitecking.domain.user.dto.UserUpdateRequestDto;
import com.laydowncoding.tickitecking.domain.user.entity.User;
import com.laydowncoding.tickitecking.domain.user.entity.UserRole;
import com.laydowncoding.tickitecking.domain.user.repository.UserRepository;
import com.laydowncoding.tickitecking.global.exception.CustomRuntimeException;
import java.util.List;
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

    @Mock
    ReservationRepository reservationRepository;

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

    @DisplayName("회원 수정 요청 성공")
    @Test
    void update_success() {
        //given
        given(userRepository.findById(anyLong())).willReturn(Optional.of(User.builder()
            .username("username")
            .password("password")
            .email("email@com")
            .nickname("nickname")
            .role(UserRole.USER)
            .build()));
        UserUpdateRequestDto requestDto = UserUpdateRequestDto.builder()
            .email("update@com")
            .nickname("updateNickname")
            .build();

        //when & then
        assertDoesNotThrow(() ->
            userService.updateUser(1L, requestDto));
    }

    @DisplayName("회원 수정 요청 실패")
    @Test
    void update_fail() {
        //given
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());
        UserUpdateRequestDto requestDto = UserUpdateRequestDto.builder()
            .email("update@com")
            .nickname("updateNickname")
            .build();

        //when & then
        assertThatThrownBy(() -> userService.updateUser(1L, requestDto))
            .isInstanceOf(CustomRuntimeException.class);
    }

    @DisplayName("회원 조회 요청 성공")
    @Test
    void get_success() {
        //given
        given(userRepository.findById(anyLong())).willReturn(Optional.of(User.builder()
            .username("username")
            .password("password")
            .email("email@com")
            .nickname("nickname")
            .role(UserRole.USER)
            .build()));

        //when
        UserResponseDto response = userService.getUser(1L);

        //then
        assertThat(response).isNotNull();
        assertThat(response.getUsername()).isEqualTo("username");
    }

    @DisplayName("회원 조회 요청 실패")
    @Test
    void get_fail() {
        //given
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> userService.getUser(1L))
            .isInstanceOf(CustomRuntimeException.class);
    }

    @DisplayName("회원 삭제 요청 성공")
    @Test
    void delete_success() {
        //given
        given(userRepository.findById(anyLong())).willReturn(Optional.of(User.builder()
            .username("username")
            .password("password")
            .email("email@com")
            .nickname("nickname")
            .role(UserRole.USER)
            .build()));

        //when & then
        assertDoesNotThrow(() -> userService.deleteUser(1L));
        verify(userRepository, times(1)).delete(any(User.class));
    }

    @DisplayName("회원 삭제 요청 실패")
    @Test
    void delete_fail() {
        //given
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> userService.deleteUser(1L))
            .isInstanceOf(CustomRuntimeException.class);
    }

    @DisplayName("회원 예매 조회 요청")
    @Test
    void get_reservation_success() {
        //given
        given(reservationRepository.findReservations(anyLong()))
            .willReturn(
                List.of(new UserReservationResponseDto(), new UserReservationResponseDto()));

        //when
        List<UserReservationResponseDto> response = userService.getReservations(1L);

        //then
        assertThat(response).isNotNull();
        assertThat(response).hasSize(2);
    }
}
