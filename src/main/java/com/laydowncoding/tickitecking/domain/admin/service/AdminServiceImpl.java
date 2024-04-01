package com.laydowncoding.tickitecking.domain.admin.service;

import com.laydowncoding.tickitecking.domain.user.dto.LoginRequestDto;
import com.laydowncoding.tickitecking.domain.user.entity.User;
import com.laydowncoding.tickitecking.domain.user.entity.UserRole;
import com.laydowncoding.tickitecking.domain.user.repository.UserRepository;
import com.laydowncoding.tickitecking.global.exception.InvalidUserException;
import com.laydowncoding.tickitecking.global.service.RedisService;
import com.laydowncoding.tickitecking.global.util.JwtUtil;
import jakarta.annotation.PostConstruct;
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
        .orElseThrow(() -> new NullPointerException("해당 유저는 존재하지 않습니다."));
    if (!passwordEncoder.matches(password, user.getPassword()) || !user.getRole().equals(UserRole.ADMIN)) {
      throw new InvalidUserException("허용되지 않은 권한입니다.");
    }

    String accessToken = jwtUtil.createAccessToken(user.getId(), loginRequest.getUsername(),
        UserRole.ADMIN.name());
    String refreshToken = jwtUtil.createRefreshToken(UserRole.ADMIN.name()).substring(7);
    redisService.setValuesWithTimeout(user.getUsername(), refreshToken, 7L);

    return accessToken;
  }
}
