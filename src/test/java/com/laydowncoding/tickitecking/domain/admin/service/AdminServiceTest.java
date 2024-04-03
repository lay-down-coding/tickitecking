package com.laydowncoding.tickitecking.domain.admin.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.laydowncoding.tickitecking.domain.admin.dto.request.AdminUserUpdateRequestDto;
import com.laydowncoding.tickitecking.domain.admin.dto.response.AdminReservationResponseDto;
import com.laydowncoding.tickitecking.domain.admin.dto.response.AdminUserResponseDto;
import com.laydowncoding.tickitecking.domain.auditorium.dto.response.AuditoriumResponseDto;
import com.laydowncoding.tickitecking.domain.auditorium.repository.AuditoriumRepository;
import com.laydowncoding.tickitecking.domain.reservations.repository.ReservationRepository;
import com.laydowncoding.tickitecking.domain.seat.entity.Seat;
import com.laydowncoding.tickitecking.domain.seat.repository.SeatRepository;
import com.laydowncoding.tickitecking.domain.user.dto.LoginRequestDto;
import com.laydowncoding.tickitecking.domain.user.entity.User;
import com.laydowncoding.tickitecking.domain.user.entity.UserRole;
import com.laydowncoding.tickitecking.domain.user.repository.UserRepository;
import com.laydowncoding.tickitecking.global.exception.CustomRuntimeException;
import com.laydowncoding.tickitecking.global.exception.InvalidUserException;
import com.laydowncoding.tickitecking.global.service.RedisService;
import com.laydowncoding.tickitecking.global.util.JwtUtil;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

  @InjectMocks
  private AdminServiceImpl adminService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private AuditoriumRepository auditoriumRepository;

  @Mock
  private SeatRepository seatRepository;

  @Mock
  private ReservationRepository reservationRepository;

  @Mock
  private JwtUtil jwtUtil;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private RedisService redisService;

  @Test
  public void testCreateAdminAccount_Success() {
    when(userRepository.existsByRole(UserRole.ADMIN)).thenReturn(false);

    adminService.createAdminAccount();

    verify(userRepository, times(1)).existsByRole(UserRole.ADMIN);
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  public void testCreateAdminAccount_AdminExists() {
    when(userRepository.existsByRole(UserRole.ADMIN)).thenReturn(true);

    adminService.createAdminAccount();

    verify(userRepository, times(1)).existsByRole(UserRole.ADMIN);
  }

  @Test
  void testLogin_Success() {
    LoginRequestDto loginRequest = new LoginRequestDto("admin", "password");
    String encodedPassword = "$2a$10$4QXdYasJ3t52ST8J1v23j.1mEohu9Q8Tn9itkJgSKazbdKwHmoK1y"; // 예상되는 암호화된 패스워드
    User user = new User(1L, "admin", encodedPassword, "admin@example.com", "admin",
        UserRole.ADMIN);

    when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
    when(passwordEncoder.matches("password", encodedPassword)).thenReturn(true);
    when(jwtUtil.createAccessToken(anyLong(), eq("admin"), eq(UserRole.ADMIN.name()))).thenReturn(
        "access_token");
    when(jwtUtil.createRefreshToken(UserRole.ADMIN.name())).thenReturn("refresh_token");
    doNothing().when(redisService).setValuesWithTimeout(eq("admin"), anyString(), eq(7L));

    String accessToken = adminService.login(loginRequest);

    assertNotNull(accessToken);
    assertEquals("access_token", accessToken);
    verify(userRepository).findByUsername("admin");
    verify(passwordEncoder).matches("password", encodedPassword);
    verify(jwtUtil).createAccessToken(anyLong(), eq("admin"), eq(UserRole.ADMIN.name()));
    verify(jwtUtil).createRefreshToken(UserRole.ADMIN.name());
    verify(redisService).setValuesWithTimeout(eq("admin"), anyString(), eq(7L));
  }

  @Test
  void testLogin_InvalidUser() {
    LoginRequestDto loginRequest = new LoginRequestDto("nonexistent", "password");

    when(userRepository.findByUsername("nonexistent")).thenReturn(java.util.Optional.empty());

    assertThrows(NullPointerException.class, () -> adminService.login(loginRequest));
    verify(userRepository).findByUsername("nonexistent");
    verifyNoMoreInteractions(passwordEncoder, jwtUtil, redisService);
  }

  @Test
  void testLogin_IncorrectPassword() {
    LoginRequestDto loginRequest = new LoginRequestDto("admin", "wrong_password");
    String encodedPassword = "$2a$10$4QXdYasJ3t52ST8J1v23j.1mEohu9Q8Tn9itkJgSKazbdKwHmoK1y";
    User user = new User(1L, "admin", encodedPassword, "admin@example.com", "admin",
        UserRole.ADMIN);

    when(userRepository.findByUsername("admin")).thenReturn(java.util.Optional.of(user));
    when(passwordEncoder.matches("wrong_password", encodedPassword)).thenReturn(false);

    assertThrows(InvalidUserException.class, () -> adminService.login(loginRequest));
    verify(userRepository).findByUsername("admin");
    verify(passwordEncoder).matches("wrong_password", encodedPassword);
    verifyNoMoreInteractions(jwtUtil, redisService);
  }

  @Test
  void testGetUsers_Success() {
    User user1 = new User(1L, "user1", "password1", "user1@example.com", "User One", UserRole.USER);
    User user2 = new User(2L, "user2", "password", "user2@example.com", "User Two", UserRole.USER);
    List<User> userList = Arrays.asList(user1, user2);

    when(userRepository.findAll()).thenReturn(userList);

    List<AdminUserResponseDto> responseDtoList = adminService.getUsers();

    assertEquals(userList.size(), responseDtoList.size());
    verify(userRepository).findAll();
  }

  @Test
  void testUpdateUser_Success() {
    AdminUserUpdateRequestDto updateRequestDto = new AdminUserUpdateRequestDto(
        "updateUsername",
        "updatePassword",
        "update@test.com",
        "updateUser",
        UserRole.COMPANY_USER
    );

    User existingUser = new User(
        1L,
        "oldUsername",
        "oldPassword",
        "oldNickname",
        "old@example.com",
        UserRole.USER);
    when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(existingUser));

    String encryptedPassword = "encryptedNewPassword";
    when(passwordEncoder.encode(updateRequestDto.getPassword())).thenReturn(encryptedPassword);

    adminService.updateUser(1L, updateRequestDto);

    verify(userRepository).findById(1L);
    verify(passwordEncoder).encode(updateRequestDto.getPassword());
    assertEquals(updateRequestDto.getUsername(), existingUser.getUsername());
    assertEquals(encryptedPassword, existingUser.getPassword());
    assertEquals(updateRequestDto.getNickname(), existingUser.getNickname());
    assertEquals(updateRequestDto.getEmail(), existingUser.getEmail());
    assertEquals(updateRequestDto.getRole().name(), existingUser.getRole().name());
  }

  @Test
  void testUpdateUser_UserNotFound() {
    AdminUserUpdateRequestDto updateRequestDto = new AdminUserUpdateRequestDto();

    when(userRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(
        CustomRuntimeException.class, () -> adminService.updateUser(1L, updateRequestDto));
  }

  @Test
  void testGetAuditoriums_Success() {
    List<AuditoriumResponseDto> auditoriumList = List.of(
        new AuditoriumResponseDto(), new AuditoriumResponseDto()
    );

    when(auditoriumRepository.getAuditoriumAll()).thenReturn(auditoriumList);

    List<AuditoriumResponseDto> result = adminService.getAuditoriums();

    assertEquals(2, result.size());
  }

  @Test
  void testLockSeat_Success() {
    Seat seat = new Seat("5", "C", "Y", "G", 1L);
    when(seatRepository.findByIdAndAuditoriumId(anyLong(), anyLong())).thenReturn(seat);

    adminService.lockSeat(1L, 1L);

    assertEquals(seat.getAvailability(), "N");
  }

  @Test
  void testGetReservations_Success() {
    AdminReservationResponseDto reservation1 = new AdminReservationResponseDto();
    AdminReservationResponseDto reservation2 = new AdminReservationResponseDto();
    List<AdminReservationResponseDto> expectedReservations = List.of(reservation1, reservation2);
    when(reservationRepository.getReservationAll()).thenReturn(expectedReservations);

    List<AdminReservationResponseDto> actualReservations = adminService.getReservations();

    assertEquals(expectedReservations.size(), actualReservations.size());
    assertTrue(actualReservations.containsAll(expectedReservations));
  }
}
