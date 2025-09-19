package org.shark.boot06.user.dto.response;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ResponseUserDTO {   //성공 시 보내줄 DTO

  private int status;
  private String message;
  private Map<String, Object> results;
  
}


