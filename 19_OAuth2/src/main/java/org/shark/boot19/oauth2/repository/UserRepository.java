package org.shark.boot19.oauth2.repository;

import java.util.Optional;

import org.shark.boot19.oauth2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findById(String id);
}
