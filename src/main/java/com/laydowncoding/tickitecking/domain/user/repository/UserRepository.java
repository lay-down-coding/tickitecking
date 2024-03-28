package com.laydowncoding.tickitecking.domain.user.repository;

import com.laydowncoding.tickitecking.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
