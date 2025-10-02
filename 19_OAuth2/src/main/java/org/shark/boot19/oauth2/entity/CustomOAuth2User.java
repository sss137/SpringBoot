package org.shark.boot19.oauth2.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;

//소셜 로그인 성공 시 SecurityContext에 저장될 사용자 정보
@Getter
public class CustomOAuth2User implements OAuth2User{
  
  private Map<String, Object> attributes;
  private User foundUser;
  
  public CustomOAuth2User(Map<String, Object> attributes, User foundUser) {
    super();
    this.attributes = attributes;
    this.foundUser = foundUser;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }
  
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + foundUser.getRole().name()));
  }
  
  @Override
  public String getName() {
    return foundUser.getKakaoId();
  }
  
}


