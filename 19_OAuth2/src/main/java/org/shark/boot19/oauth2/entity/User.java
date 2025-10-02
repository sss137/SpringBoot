package org.shark.boot19.oauth2.entity;

import org.shark.boot19.oauth2.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "kakao_id", unique = true)
  private String kakaoId;
  
  @Column(name = "profile_nickname")
  private String profileNickname;
  
  @Column(name = "profile_image")
  private String profileImage;
  
  @Enumerated(EnumType.STRING)
  private Role role;
  
  protected User() {}
  
  public static User createUser(String kakaoId, String profileNickname, String profileImage) {
    User user = new User();
    user.kakaoId = kakaoId;
    user.profileNickname = profileNickname;
    user.profileImage = profileImage;
    user.role = Role.USER;
    return user;
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", kakaoId=" + kakaoId + ", profileNickname=" + profileNickname + ", profileImage="
        + profileImage + ", role=" + role + "]";
  }
  
}


