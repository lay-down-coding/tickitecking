package com.laydowncoding.tickitecking.domain.user.entity;

import com.laydowncoding.tickitecking.domain.user.dto.UserUpdateRequestDto;
import com.laydowncoding.tickitecking.global.entity.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction(value = "deleted_at is NULL")
public class User extends Timestamp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String email;

  @Column(unique = true)
  private String nickname;

  @Column
  @Enumerated(value = EnumType.STRING)
  private UserRole role;

  @Builder
  public User(Long id, String username, String password, String email, String nickname,
      UserRole role) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
    this.nickname = nickname;
    this.role = role;
  }

  public User(Long id, String username, String role) {
    this.id = id;
    this.username = username;
    this.role = UserRole.valueOf(role);
  }

  public void update(UserUpdateRequestDto requestDto) {
    this.email = requestDto.getEmail();
    this.nickname = requestDto.getNickname();
  }
}
