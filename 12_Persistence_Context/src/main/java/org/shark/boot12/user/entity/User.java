package org.shark.boot12.user.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.shark.boot12.user.enums.Gender;

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
  @Column(name = "uid")
  private Long id;
  
  @Column(nullable = false)
  private String username;
  
  @Column(unique = true)
  private String email;
  
  @Enumerated(EnumType.STRING)
  private Gender gender;
  
  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;
  
  @Column(name = "withraw_yn")   
  private Boolean withrawYn = false;   //탈퇴 여부
  
  //디폴트 생성자는 필수로 등록
  protected User() {}   //외부에서 new User() 호출을 막음으로써 필드값들이 null이 되는 것을 방지
  
  //정적 팩토리메소드(=생성자 역할 수행) 형식으로 User 엔티티 생성 
  public static User createUser(String username, String email, Gender gender) {
    User user = new User();
    user.username = username;
    user.email = email;
    user.gender = gender;
    return user;
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", username=" + username + ", email=" + email + ", gender=" + gender + ", createdAt="
        + createdAt + ", withrawYn=" + withrawYn + "]";
  }
  
}


