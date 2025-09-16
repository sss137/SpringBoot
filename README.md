# :thumbsup: Spring Boot

### :rocket: Spring Boot 출연 배경
- 기존 기술(EJB)의 복잡성을 크게 줄인 스프링 프레임워크(Spring Framework)가 2004년 3월에 1.0 버전이 공식 출시되었습니다.
- 기존 기술에 비하면 많이 줄었지만, 여전히 여러 가지 사항들을 개발자가 직접 설정해줘야하는 불편함이 존재하였습니다.
- 스프링 부트(Spring Boot)는 이러한 단점을 극복하기 위해서 만들어진 더 현대적인 프레임워크입니다.



### :rocket: Spring Boot 특징
- 2014년 4월에 처음 발표된 스프링 부트는 스프링 레거시(Spring Framework)에 비해 개발 편의성과 생산성이 향상된 현대적인 프레임워크입니다.
- 환경 변수, 빈 등록 등 개발자가 직접 수행하던 작업을 대부분 자동화하여 복잡한 설정 없이도 빠르게 개발 환경을 구축할 수 있습니다.
- 주요 차이점 요약

  | 항목        | 스프링 레거시(Spring Framework)     | 스프링 부트(Spring Boot) |
  |-------------|-------------------------------------|---------------------------|
  | 설정        | 복잡한 XML/Java 설정 필요           | 자동 설정 지원 |
  | 서버        | 외부 WAS(Tomcat) 직접 설치          | 내장 WAS 제공(내장 Tomcat, Jetty 등) |
  | 의존관리    | 의존성 수동 관리(Maven/Gradle)      | 스타터(Starter)를 이용한 자동 관리 |
  | 빈 등록     | XML/Java 설정에 직접 명시           | 컴포넌트 자동 스캔 및 등록 (@SpringBootApplication) |
  | 배포/실행   | 배포 파일(WAR) 생성 후 WAS로 실행   | 단일 JAR 파일로 곧바로 실행 |



### :rocket: JDK 변경 및 툴 변경
- 주요 항목 비교

  |항목        | 스프링 레거시(Spring Framework)     | 스프링 부트(Spring Boot)|
  |-------------|------------------------------------|--------------------------|
  |JDK         | JDK-11                              | JDK-17+|
  |IDE         | STS3 (3.9.18.RELEASE)               | STS4 (최신버전 - 2025.09.15 기준 / 4.32.0.RELEASE)|



### :rocket: STS4 설정
- Lombok 설치
- Eclipse Enterprises Java and Web Developer Tools 3.xx 설치 (View 관련 에디터 메뉴 제공)  
  - STS4는 View 관련 에디터 메뉴가 포함되어 있지 않습니다.
  - STS4는 Eclipse 기반이고 웹 개발을 위한 View 편집 기능(예: JSP, HTML, XML 편집기 등)은 기본 Eclipse 설치에 포함되는 추가 플러그인이나 확장 기능으로 제공됩니다.
  - 기본 STS4 설치 시에는 경량화와 빠른 실행을 위해 필수 기능 중심으로만 제공되며, 웹 관련 뷰 메뉴나 에디터가 보이지 않을 경우 별도의 플러그인을 설치해야 합니다.
  - 설치 방법 : [Help] > [Eclipse Marketplace] > "web" 검색 후 설치 진행 후 STS4 재실행
- [Window] - [Preferences...]  
  테마, 폰트, 인코딩, 들여쓰기, Syntax Coloring, Limit console output



### :rocket: 프로젝트 생성 및 초기 세팅 
- Create new Spring Starter Project

  |항목          | 디폴트 값                    | 의미 |
  |--------------|------------------------------|-------------|
  |Service URL   | https://start.spring.io      | 스타터 프로젝트를 제공 |
  |Name          | demo                         | 프로젝트이름 |
  |Type          | Gradle - Groovy              | 빌드 툴 |
  |Packaging     | Jar                          | 패키징 방식 (Jar, War) |
  |Java Version  | 17                           | 자바 버전 (17+ 필요) |
  |Language      | Java                         | 사용 언어 |
  |Group         | com.example                  | 그룹 ID |
  |Artifact      | demo                         | 아티팩트 ID |
  |Version       | 0.0.1-SNAPSHOT               | 프로젝트 버전 |
  |Description   | Demo project for Spring Boot | 프로젝트 설명 |
  |Package       | com.example.demo             | 베이스 패키지 |



### :rocket: Dependencies (MyBatis를 활용한 CRUD 구현 시)

**1) Spring Boot Version : 3.5.5 (2025.09.15 기준)**  
**2) 주요 Dependencies**  
```
▶ Developer Tools  
  - Spring Boot DevTools  
  - Lombok  
▶ SQL  
  - MyBatis Framework  
  - MySQL Driver (완성 후 8.0.33 버전 명시 필요)  
▶ Template Engines  
  - Thymeleaf  
▶ Web  
  - Spring Web
```
  ※ 주의사항  
    디펜던시 등록 후 필요한 설정을 하지 않으면 프로젝트 실행 시 오류가 발생할 수 있습니다.  
    디펜던시를 등록했으면 프로젝트 실행 전에 반드시 관련 설정을 해 둬야 합니다.  
    예) DB 관련 디펜던시를 선택한 후 DB 접속 정보가 없는 경우 오류 발생  



### :rocket: 프로젝트 구조 확인
```
- src/main/java
   ㄴ com.example.project
     ㄴ Application.java  ················· @SpringBootApplication
- src/main/resources
   ㄴ template  ··························· Template Engine을 사용하는 HTML
   ㄴ static    ··························· 정적 파일
   ㄴ application.properties  ············· 프로퍼티 파일 (환경 설정)
- src/test/java
   ㄴ com.example.project
     ㄴ ApplicationTests.java  ············ 테스트 파일
- build.gradle  ··························· 의존 관리 파일
```



### :rocket: spring boot starter web 디펜던시
- Spring MVC 기반의 웹 개발을 위한 필수 라이브러리의 묶음
- 주요 포함 디펜던시

  |디펜던시                   | 기능 |
  |---------------------------|-------|
  |spring-boot-starter        | 공통적으로 적용되는 로깅 등 기본적인 Spring Boot 설정 포함|
  |spring-boot-starter-tomcat | 내장 톰캣 서버(기본) 포함 — WAS 설치 없이 바로 웹 애플리케이션 실행 가능|
  |spring-boot-starter-json   | JSON 변환 라이브러리(Jackson)로 REST API에서 객체-JSON 매핑 지원|
  |spring-web, spring-webmvc  | 스프링 MVC, REST Controller, 필터, 인터셉터 등 웹 애플리케이션의 핵심 컴포넌트|
  |spring-boot-http-converter | HTTP 요청/응답 메시지 변환 지원|
  |servlet-api (compileOnly)  | 서블릿 기반 웹 개발을 위한 인터페이스 제공(컴파일 시에만)|
  |slf4j                      | 로그 추상화 프레임워크(SLF4J), Logback 등 통합 로깅 지원|
    


### :rocket: 스프링 레거시를 스프링 부트로 마이그레이션하기

#### :star: 1. 웹 화면 View
```
스프링 레거시(Spring Framework)  
▶ 웹 화면 : JSP
  src/main/webapp/WEB-INF/views 경로에서 JSP 사용
▶ 뷰 리졸버 : servlet-context.xml에서 설정
  <beans:bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <beans:property name="prefix" value="/WEB-INF/views/" />
    <beans:property name="suffix" value=".jsp" />
  </beans:bean>
```
```
스프링 부트(Spring Boot)  
▶ 웹 화면 : Template Engine 중 하나인 Thymeleaf 사용
  src/main/resources/templates 경로에서 HTML 사용
▶ 뷰 리졸버 : 뷰 리졸버 설정을 부트가 알아서 관리
```


#### :star: 2. Component Scan
```
스프링 레거시(Spring Framework)
▶ Component Scan : servlet-context.xml
  <context:component-scan base-package="com.example.project" />
```
```
스프링 부트(Spring Boot)
▶ Component Scan : 메인 클래스
  @SpringBootApplication 어노테이션에 @ComponentScan 포함되어 자동 스캔
```


#### :star: 3. CharacterEncodingFilter 필터 설정
```
스프링 레거시(Spring Framework)
▶ CharacterEncodingFilter 필터 : web.xml
  <filter>
    <filter-name>encodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
    <init-param>
      <param-name>forceEncoding</param-name>
      <param-value>true</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>encodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
```
```
스프링 부트(Spring Boot)
▶ CharacterEncodingFilter : application.properties
  spring.web.encoding.charset=UTF-8
  spring.web.encoding.enabled=true
  spring.web.encoding.force=true
    또는
  server.servlet.encoding.charset=UTF-8
  server.servlet.encoding.enabled=true
  server.servlet.encoding.force=true
```

#### :star: 4. 정적 자원 관리
```
스프링 레거시(Spring Framework)
▶ 저장 경로
  src/main/webapp/resources
▶ 경로 매핑 : servlet-context.xml
  <resources location="/resources/" mapping="/resources/**" />
```
```
스프링 부트(Spring Boot)
▶ 저장 경로
  src/main/resources/static
▶ 경로 매핑 : WebMvcConfigurer 구현클래스
  @Configuration
  public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/static/**")
              .addResourceLocations("classpath:/static/");
    }  
  }
```


### :star: 5. schema.sql / data.sql
```
스프링 레거시(Spring Framework)
▶ 쿼리 실행
  src/main/resources 아래 저장해 두고 직접 실행
```
```
스프링 부트(Spring Boot)
▶ 쿼리 실행
  src/main/resources 아래 저장해 두고 부트 앱 실행 시 자동으로 실행할 수 있도록 설정 가능
▶ 쿼리 자동 실행 방법 : application.properties
  spring.sql.init.mode=always
```


### :star: 6. 커넥션 풀 설정
```
스프링 레거시(Spring Framework)
▶ 커넥션 풀 : servlet-context.xml
  <beans:bean class="com.zaxxer.hikari.HikariConfig" id="hikariConfig">
    <beans:property name="driverClassName" value="com.mysql.cj.jdbc.Driver" />
    <beans:property name="jdbcUrl" value="jdbc:mysql://localhost:3306/dbname" />
    <beans:property name="username" value="dbuser" />
    <beans:property name="password" value="dbpass" />
  </beans:bean>
  <beans:bean class="com.zaxxer.hikari.HikariDataSource" id="hikariDataSource">
    <beans:constructor-arg ref="hikariConfig" />
  </beans:bean>
```
```
스프링 부트(Spring Boot)
▶ 커넥션 풀 : application.properties
  spring.datasource.hikari.maximum-pool-size=10
  spring.datasource.url=jdbc:mysql://localhost:3306/dbname
  spring.datasource.username=dbuser
  spring.datasource.password=dbpass
  # spring-boot-starter-jdbc, spring-boot-starter-data-jpa : HikariCP DataSource 내장
```


### :star: 7. MyBatis 설정
```
스프링 레거시(Spring Framework)
▶ SqlSesssionFactory 관련 빈 설정 : servlet-context.xml
  <beans:bean class="org.mybatis.spring.SqlSessionFactoryBean" id="sqlSessionFactoryBean">
    <beans:property name="dataSource" ref="hikariDataSource" />
    <beans:property name="configLocation" value="classpath:mybatis/config/mybatis-config.xml" />
    <beans:property name="mapperLocations" value="classpath:mybatis/mapper/*.xml" />
  </beans:bean>
  <beans:bean class="org.mybatis.spring.SqlSessionTemplate" id="sqlSessionTemplate">
  <beans:constructor-arg ref="sqlSessionFactoryBean" />
</beans:bean>
▶ mapUnderscoreToCamelCase 설정 : mybatis-config.xml
  <setting name="mapUnderscoreToCamelCase" value="true"/>
▶ typeAliases : mybatis-config.xml
  <typeAliases>
    <typeAlias type="com.example.dto.UserDTO" alias="UserDTO"/>
    <typeAlias type="com.example.dto.BoardDTO" alias="BoardDTO"/>
  </typeAliases>
▶ typeHandlers
  <typeHandlers>
    <typeHandler javaType="java.time.LocalDateTime"
                 handler=com.example.handler.LocalDateTimeTypeHandler"/>
  </typeHandlers>
```
```
스프링 부트(Spring Boot)
▶ SqlSesssionFactory 관련 빈 설정 : mybatis-spring-boot-starter 디펜던시 + application.properties
  mybatis.config-location=classpath:mybatis/config/mybatis-config.xml
  mybatis.mapper-locations=classpath:mybatis/mapper/*.xml
▶ mapUnderscoreToCamelCase 설정 : application.properties
  mybatis.configuration.map-underscore-to-camel-case=true
▶ typeAliases : application.properties
  type-aliases-package: com.example.dto  # 패키지 단위로 등록
▶ typeHandlers : mybatis-config.xml
  <typeHandlers>
    <typeHandler javaType="java.time.LocalDateTime"
                 handler="com.example.handler.LocalDateTimeTypeHandler"/>
  </typeHandlers>
```


### :star: 8. 트랜잭션 매니저 및 AOP 활성화
```
스프링 레거시(Spring Framework)
▶ 트랜잭션 매니저 : servlet-context.xml
  <beans:bean class="org.springframework.jdbc.datasource.DataSourceTransactionManager"
              id="transactionManager">
  </beans:bean>
▶ 트랜잭션 매니저 활성화 : servlet-context.xml
  <tx:annotation-driven transaction-manager="transactionManager" />   
  <aop:aspectj-autoproxy />   
```
```
스프링 부트(Spring Boot)
▶ 트랜잭션 매니저 : DataSourceTransactionManager를 자동으로 생성하며, 별도 선언이 필요 없음
▶ 트랜잭션 매니저 활성화 : @Configuration 클래스
  클래스 레벨 어노테이션 @EnableTransactionManagement 추가
  클래스 레벨 어노테이션 @EnableAspectJAutoProxy 추가
```


### :star: 9. 파일 첨부
```
스프링 레거시(Spring Framework)
▶ StandardServletMultipartResolver : web.xml, root-context.xml
  * web.xml
    <multipart-config>
      <location>C:\upload\tmp</location>
      <max-file-size>10485760</max-file-size>  <!-- 10MB -->
      <max-request-size>104857600</max-request-size>  <!-- 100MB -->
      <file-size-threshold>0</file-size-threshold>
    </multipart-config>
  * root-context.xml
    <bean class="org.springframework.web.multipart.support.StandardServletMultipartResolver"
          id="multipartResolver" />
▶ CommonsMultipartResolver : root-context.xml
  <bean class="org.springframework.web.multipart.commons.CommonsMultipartResolver"
        id="multipartResolver">
    <property name="defaultEncoding" value="UTF-8"/>
    <property name="maxUploadSize" value="104857600"/>  <!-- 10MB -->
    <property name="maxUploadSizePerFile" value="10485760"/>  <!-- 100MB -->
    <property name="uploadTempDir" value="file:/C:/upload/tmp"/>
    <beans:property name="maxInMemorySize" value="10485760"/>
  </bean>
```
```
스프링 부트(Spring Boot)
▶ StandardServletMultipartResolver, CommonsMultipartResolver : application.properties
  spring.servlet.multipart.location=C:/upload/tmp
  spring.servlet.multipart.max-file-size=10MB
  spring.servlet.multipart.max-request-size=100MB
  # 메모리 임계 설정(file-size-threshold)는 별도 프로퍼티가 없음
```


### :star: 10. 인터셉터
```
스프링 레거시(Spring Framework)
▶ 인터셉터 설정 : servlet-context.xml
  <interceptors>
    <interceptor>
      <mapping path="/board/write" />
      <mapping path="/user/myinfo" />
      <beans:bean class="com.example.interceptor.LoginCheckInterceptor" />
    </interceptor>
  </interceptors>
```
```
스프링 부트(Spring Boot)
▶ 인터셉터 설정 : WebMvcConfigurer 구현클래스
  @Configuration
  public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(new LoginCheckInterceptor()).addPathPatterns("/board/write", "/user/myinfo");
    }
  }
```


### :star: 11. 스케쥴러
```
스프링 레거시(Spring Framework)
▶ 스케쥴러 설정 : servlet-context.xml
  <task:annotation-driven />
```
```
스프링 부트(Spring Boot)
▶ 스케쥴러 설정 : @Configuration 클래스
  클래스 레벨 어노테이션 @EnableScheduling 추가
```


### :star: 12. 메일 보내기
```
스프링 레거시(Spring Framework)
▶ 필요 디펜던시
  dependencies {
    implementation "org.springframework:spring-context-support"
    implementation "javax.mail:javax.mail-api:1.6.2"
    implementation "com.sun.mail:javax.mail:1.6.2"
  }
▶ 메일 보내기 설정 : root-context.xml
  <bean class="org.springframework.mail.javamail.JavaMailSenderImpl" id="javaMailSender">
    <property name="host" value="smtp.naver.com" />
    <property name="port" value="465" />
    <property name="username" value="네이버계정@naver.com" />
    <property name="password" value="앱비밀번호" />
    <property name="defaultEncoding" value="UTF-8" />
    <property name="javaMailProperties">
      <props>
        <prop key="mail.smtp.auth">true</prop>
        <prop key="mail.smtp.ssl.enable">true</prop>
        <prop key="mail.smtp.starttls.enable">true</prop>
        <prop key="mail.smtp.starttls.required">true</prop>
        <prop key="mail.smtp.ssl.trust">smtp.naver.com</prop>
      </props>
    </property>
  </bean>
```
```
스프링 부트(Spring Boot)
▶ 필요 디펜던시
  dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-mail'
  }
▶ 메일 보내기 설정 : application.properties
  spring.mail.host=smtp.naver.com
  spring.mail.port=465
  spring.mail.username=네이버계정@naver.com
  spring.mail.password=앱비밀번호
  spring.mail.default-encoding=UTF-8
  spring.mail.properties.mail.smtp.auth=true
  spring.mail.properties.mail.smtp.ssl.enable=true
  spring.mail.properties.mail.smtp.starttls.enable=true
  spring.mail.properties.mail.smtp.starttls.required=true
  spring.mail.properties.mail.smtp.ssl.trust=smtp.naver.com
```


### :star: 13. 웹소켓
```
스프링 레거시(Spring Framework)
▶ 웹 소켓 설정 : servlet-context.xml
  <websocket:message-broker application-destination-prefix="/websocket/stomp">
    <websocket:stomp-endpoint path="/ws">
      <websocket:handshake-interceptors>
        <bean class="org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor"/>
      </websocket:handshake-interceptors>      
      <websocket:sockjs />
    </websocket:stomp-endpoint>
    <websocket:simple-broker prefix="/topic" />
  </websocket:message-broker>
```
```
스프링 부트(Spring Boot)
▶ 웹 소켓 설정 : WebSocketMessageBrokerConfigurer 구현클래스
  @Configuration
  @EnableWebSocketMessageBroker
  public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
      registry.addEndpoint("/ws")
              .addInterceptors(new HttpSessionHandshakeInterceptor()).withSockJS();
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
      registry.enableSimpleBroker("/topic");
      registry.setApplicationDestinationPrefixes("/websocket/stomp");
    }
  }
```


### :star: 14. JUnit
```
스프링 레거시(Spring Framework)
▶ JUnit4
  @RunWith(SpringJUnit4ClassRunner.class)
  @ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
  public class ApplicationTests { }
```
```
스프링 부트(Spring Boot)
▶ JUnit Jupiter API (JUnit5)
  @SpringBootTest
  public class ApplicationTests { }
```


### :star: 15. 컨텍스트 패스
```
스프링 레거시(Spring Framework)
▶ 컨텍스트 패스
  - 컨텍스트 패스 기본값이 artifactId
```
```
스프링 부트(Spring Boot)
▶ 컨텍스트 패스
  - 컨텍스트 패스 기본값이 / (루트 컨텍스트)
  - application.properties 설정을 통해 컨텍스트 패스 변경 가능
    server.servlet.context-path=/myapp
```
