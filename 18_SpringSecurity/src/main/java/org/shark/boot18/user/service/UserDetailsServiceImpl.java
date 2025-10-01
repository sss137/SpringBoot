package org.shark.boot18.user.service;

import java.util.Collections;

import org.shark.boot18.user.entity.LoginUser;
import org.shark.boot18.user.entity.User;
import org.shark.boot18.user.repository.LoginRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final LoginRepository loginRepository;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //테스트 (계정명이 "goodee"이면 "goodee/1234" 계정이 맞는지 체크)
//    if (username.equals("goodee")) {
//      return new LoginUser("goodee", "1234", Collections.emptyList());
//    } else {
//      throw new UsernameNotFoundException(username + "사용자가 존재하지 않습니다.");
//    }
    
    //DB에서 사용자 정보를 불러오는 작업을 수행합니다.
    User foundUser = loginRepository.findByUsername(username).orElse(null);
    if (foundUser == null) {
      throw new UsernameNotFoundException(username + "사용자가 존재하지 않습니다.");
    }
    
    //DB에서 조회한 사용자 정보를 UserDetails 타입(인터페이스)의 객체(구현 객체)로 반환합니다.
    //반환한 객체가 가진 값을 이용해 Spring Security는 내부에서 비교를 진행합니다.
    return new LoginUser(foundUser.getUsername(), foundUser.getPassword(), Collections.emptyList());
    
  }

}


