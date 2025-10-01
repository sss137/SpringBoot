package org.shark.boot18.user.repository;

import java.util.Optional;

import org.shark.boot18.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<User, Integer>{

  //username을 이용한 조회
  Optional<User> findByUsername(String username);
  
}


