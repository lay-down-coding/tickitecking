package com.laydowncoding.tickitecking.domain.admin.service;

import static com.laydowncoding.tickitecking.global.exception.errorcode.UserErrorCode.NOT_FOUND_USER;
import static com.laydowncoding.tickitecking.global.exception.errorcode.UserErrorCode.UNVERIFIED_USER;

import com.laydowncoding.tickitecking.domain.admin.dto.request.AdminLockSeatRequestDto;
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
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final RedisService redisService;
  private final AuditoriumRepository auditoriumRepository;
  private final SeatRepository seatRepository;
  private final ReservationRepository reservationRepository;


  @Value("${admin.username}")
  private String adminUsername;

  @Value("${admin.password}")
  private String adminPassword;

  @Value("${admin.email}")
  private String adminEmail;

  @PostConstruct
  @Transactional
  public void createAdminAccount() {
    boolean existsAdmin = userRepository.existsByRole(UserRole.ADMIN);
    if (!existsAdmin) {
      String encrytedPassword = passwordEncoder.encode(adminPassword);
      User admin = new User(1L, adminUsername, encrytedPassword, adminEmail, "admin",
          UserRole.ADMIN);
      userRepository.save(admin);
    }
  }

  @Override
  public String login(LoginRequestDto loginRequest) {
    String password = loginRequest.getPassword();
    User user = userRepository.findByUsername(loginRequest.getUsername())
        .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_USER.getMessage()));
    if (!passwordEncoder.matches(password, user.getPassword()) || !user.getRole()
        .equals(UserRole.ADMIN)) {
      throw new CustomRuntimeException(UNVERIFIED_USER.getMessage());
    }

    String accessToken = jwtUtil.createAccessToken(user.getId(), loginRequest.getUsername(),
        UserRole.ADMIN.name());
    String refreshToken = jwtUtil.createRefreshToken(UserRole.ADMIN.name()).substring(7);
    redisService.setValuesWithTimeout(user.getUsername(), refreshToken, 7L);

    return accessToken;
  }

  @Override
  public List<AdminUserResponseDto> getUsers() {
    List<User> userList = userRepository.findAll();

    return userList.stream().map(
        user -> new AdminUserResponseDto(user.getId(), user.getUsername(), user.getEmail(),
            user.getNickname(),
            user.getRole().getAuthority())).collect(
        Collectors.toList());
  }

  @Override
  @Transactional
  public void updateUser(Long userId, AdminUserUpdateRequestDto userUpdateRequest) {
    User user = userRepository.findById(userId).orElseThrow(
        () -> new CustomRuntimeException(NOT_FOUND_USER.getMessage())
    );

    String encryptedPassword = passwordEncoder.encode(userUpdateRequest.getPassword());

    user.forceUpdate(
        userUpdateRequest.getUsername(),
        encryptedPassword,
        userUpdateRequest.getNickname(),
        userUpdateRequest.getEmail(),
        userUpdateRequest.getRole().name()
    );
  }

  @Override
  public List<AuditoriumResponseDto> getAuditoriums() {
    return auditoriumRepository.getAuditoriumAll();
  }

  @Override
  @Transactional
  public void lockSeat(Long auditoriumId, AdminLockSeatRequestDto requestDto) {
    List<Seat> seats = seatRepository.findAllByAuditoriumIdAndHorizontalAndVertical(
        auditoriumId, requestDto.getHorizontal(), requestDto.getVertical());
    for (Seat seat: seats) {
      seat.togleLock();
    }
  }

  @Override
  public List<AdminReservationResponseDto> getReservations() {
    return reservationRepository.getReservationAll();
  }
}
