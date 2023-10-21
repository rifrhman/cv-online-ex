package com.contoh.cv.repository;

import com.contoh.cv.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsernameOrEmail(String username, String email);
  Boolean existsByUsernameContains(String username);
  Boolean existsByEmail(String email);

  User findByConfirmationToken(String token);
}
