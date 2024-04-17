package com.laydowncoding.tickitecking.global.config;

import com.laydowncoding.tickitecking.global.filter.JwtAccessDeniedHandler;
import com.laydowncoding.tickitecking.global.filter.JwtAuthenticationEntryPoint;
import com.laydowncoding.tickitecking.global.filter.JwtAuthenticationFilter;
import com.laydowncoding.tickitecking.global.filter.JwtAuthorizationFilter;
import com.laydowncoding.tickitecking.global.service.RedisService;
import com.laydowncoding.tickitecking.global.service.UserDetailsServiceImpl;
import com.laydowncoding.tickitecking.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final JwtUtil jwtUtil;
  private final UserDetailsServiceImpl userDetailsService;
  private final AuthenticationConfiguration authenticationConfiguration;
  private final RedisService redisService;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
    JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil, redisService);
    filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
    return filter;
  }

  @Bean
  public JwtAuthorizationFilter jwtAuthorizationFilter() {
    return new JwtAuthorizationFilter(jwtUtil, userDetailsService, redisService);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // CSRF 설정
    http.csrf((csrf) -> csrf.disable());

    // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
    http.sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(
        SessionCreationPolicy.STATELESS));

    http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests.requestMatchers(
            PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용 설정
        .requestMatchers("/").permitAll() // 메인 페이지 요청 허가
        .requestMatchers("/api/v1/users/**").permitAll() // '/api/v1/user/'로 시작하는 요청 모두 접근 허가
        .requestMatchers("/api/v1/admin/login").permitAll()
        .requestMatchers("/actuator/**").permitAll()
        .anyRequest().authenticated() // 그 외 모든 요청 인증처리
    );

    // 필터 관리
    http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    http.exceptionHandling(
        (handler) -> handler.authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler));
    return http.build();
  }
}
