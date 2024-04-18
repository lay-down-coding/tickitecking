package com.laydowncoding.tickitecking.domain.user.repository;

import com.laydowncoding.tickitecking.domain.user.entity.User;
import com.laydowncoding.tickitecking.domain.user.entity.UserRole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

  boolean existsByRole(UserRole userRole);
}
