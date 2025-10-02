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
  private Long uid;
  
  @Column(unique = true)
  private String id;
  
  private String nickname;
  
  private String email;
  
  @Column(name = "profile_image")
  private String profileImage;
  
  @Enumerated(EnumType.STRING)
  private Role role;
  
  protected User() {}
  
  public static User CreateUser(String id, String nickname, String email, String profileImage) {
    User user = new User();
    user.id = id;
    user.nickname = nickname;
    user.email = email;
    user.profileImage = profileImage;
    user.role = Role.USER;
    return user;
  }

  @Override
  public String toString() {
    return "User [uid=" + uid + ", id=" + id + ", nickname=" + nickname + ", profileImage="
        + profileImage + ", role=" + role + "]";
  }
  
}
