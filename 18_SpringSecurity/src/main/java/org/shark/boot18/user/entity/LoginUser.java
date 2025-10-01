package org.shark.boot18.user.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


/**
 * 사용자의 인증 정보를 저장하는 UserDetails 구현 클래스
 */
public class LoginUser extends User {

  public LoginUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  }
  
}
