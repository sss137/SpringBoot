package org.shark.boot18;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//시큐리티의 가장 핵심 단어 - 인증(Authentication), 인가(Authorization)
@SpringBootApplication   //스프링 시큐리티 기본 설정 제외하기 - @SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Application {

	public static void main(String[] args) {
	  PasswordEncoder encoder = new BCryptPasswordEncoder();
	  System.out.println("암호화된 1234:" + encoder.encode("1234"));
	  SpringApplication.run(Application.class, args);
	}

}
