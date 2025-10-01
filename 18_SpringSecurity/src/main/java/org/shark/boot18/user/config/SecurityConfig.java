package org.shark.boot18.user.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
   // @Bean
  InMemoryUserDetailsManager inMemoryUserDetailsManager() {
    UserDetails user = User.withUsername("goodee")
                           .password("{noop}1234")   //{noop} : 암호화를 하지 않겠습니다.
                           .authorities("ROLE_USER")
                           .build();
    return new InMemoryUserDetailsManager(user);
  }
 
  /* 
   * SecurityFilterChain
   * Spring Security 기본 보안 구성을 담당하는 인터페이스입니다.
   * 사용자가 SecurityFilterChain 빈을 등록하면 기본 보안 구성을 비활성화합니다. (사용자가 직접 구성합니다.)
   */
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    //URL에 따른 접근 권한 부여하기
    httpSecurity.authorizeHttpRequests(auth -> auth
                                              .requestMatchers("/login", "/signup").permitAll()   //인증절차를 거치지 않는 주소를 등록합니다.
                                                                                                  //로그인 화면 이동, 회원가입 화면 이동
                                              .anyRequest().authenticated());   //나머지 모든 요청은 인증절차가 필요합니다.
    
    //csrf 비활성화 (csrf 공격 방지를 위한 토큰 처리 없음 + GET 방식 로그아웃 가능)
    httpSecurity.csrf(csrf -> csrf.disable());
    
    //폼 로그인 설정
    httpSecurity.formLogin(form -> form
                                  .loginPage("/login")  //커스텀 로그인 페이지 URL 지정
                                  .usernameParameter("username")   //사용자명의 name 속성 지정
                                  .passwordParameter("password")   //비밀번호의 name 속성 지정
                                  .loginProcessingUrl("/authentication")   //로그인을 처리할 주소
                                  .defaultSuccessUrl("/", true)   //로그인 성공 시 리다이렉트할 URL 지정
                                  .failureUrl("/login?error")   //로그인 실패시 리다이렉트할 URL 지정 (로그인 페이지로 error 파라미터를 가지고 이동)
                           );
    
    //로그아웃 설정
    httpSecurity.logout(logout -> logout
                                 .logoutUrl("/logout")   //로그아웃할 URL 지정
                                 .logoutSuccessUrl("/login?logout")   //로그아웃 성공 시 리다이렉트할 URL 지정
                                 .invalidateHttpSession(true)   //로그아웃 시 세션 무효화
                                 .deleteCookies("JSESSIONID")   //로그아웃 시 쿠키 삭제
                        );
    //SecurityFilterChain 내부에 속한 필터 동작 확인하기
    SecurityFilterChain chain = httpSecurity.build();
    chain.getFilters().stream().forEach(System.out::println);
    return chain;
  }
  
  /*
   * WebSecurityCustomizer
   * 정적 디렉터리(static) 하위 모든 리소스는 인증절차를 무시합니다.
   */
  @Bean
  WebSecurityCustomizer webSecurityCustomizer() {
    WebSecurityCustomizer webSecurityCustomizer = web -> web
                                                        .ignoring()   //인증 절차 무시
                                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    return webSecurityCustomizer;
  }
  
}


