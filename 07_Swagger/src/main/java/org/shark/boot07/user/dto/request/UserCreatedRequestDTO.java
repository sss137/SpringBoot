package org.shark.boot07.user.dto.request;

import java.time.LocalDateTime;

import org.shark.boot07.user.dto.UserDTO;

import io.swagger.v3.oas.annotations.media.Schema;
//import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 사용자 등록 DTO
 * username, password, nickname의 유효성 검사가 있습니다.
 */

@Schema(description = "사용자 등록을 위한 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserCreatedRequestDTO {

  @Schema(description = "사용자 번호(식별자)")
  private Long uid;
  
  @Schema(description = "아이디(이메일)",
          nullable = false,
          example = "id@example.com")
//  @Email(message = "이메일 형식이 올바르지 않습니다.")   //표준 이메일 검증 방식으로 검증 기능이 부족합니다.
  @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
           message = "이메일 형식이 올바르지 않습니다.")
  @NotBlank(message = "아이디는 필수입니다.")
  @Size(max = 100, message = "아이디의 최대 글자수는 100자입니다.")
  private String username;
  
  @Schema(description = "비밀번호",
          nullable = false)
  @NotBlank(message = "비밀번호는 필수입니다.")
  @Size(max = 100, message = "비밀번호의 최대 글자수는 100자입니다.")
  private String password;
  
  @Schema(description = "닉네임",
          nullable = false)
  @NotBlank(message = "닉네임는 필수입니다.")
  @Size(max = 100, message = "닉네임의 최대 글자수는 100자입니다.")
  private String nickname;
  
  @Schema(description = "등록일시")
  private LocalDateTime createdAt;
 
  //UserCreatedRequestDTO to UserDTO
  public UserDTO toUserDTO() {
    return UserDTO.builder()
                   .uid(uid)
                   .username(username)
                   .password(password)
                   .nickname(nickname)
                   .createdAt(createdAt)
                   .build();
  }
  
}


