package org.shark.appboard.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing   //Spring Data JPA의 Auditing 기능 활성화(@CreatedDate, @LastModifiedDate 동작)
public class JpaConfig {

  
  
}


