package org.shark.appboard.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  /*
   * CORS (Corss-Origin Resource Sharing)
   * 1. 교차 출처 자원 공유
   * 2. 브라우저가 자신의 출처가 아닌 다른 출처로부터 자원을 로딩하는 것을 허용하도록 서버가 허가하는 HTTP 헤더 기반 알고리즘입니다.
   *    (보안 상 이유로 브라우저는 다른 출처의 HTTP 요청을 제한합니다.)
   * 3. XMLHttpRequest, fetch(), axios 등은 모두 CORS 정책을 따릅니다.
   * 4. 스프링 부트 서버(http://localhost:8080)와 리액트 서버(http://localhost:3000)는 서로 다른 출처(=포트가 다르다)로 인식되므로 CORS 허용 작업이 필요합니다. 
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELET", "OPTIONS")
            .allowedHeaders("Authorization", "Content-Type", "Accept");
  }
  
  
}


