package com.laydowncoding.tickitecking.domain.user.entity;

import com.laydowncoding.tickitecking.global.entity.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
@SQLRestriction(value = "deleted_at is NULL")
public class User extends Timestamp {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String nickname;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRole role;
}
