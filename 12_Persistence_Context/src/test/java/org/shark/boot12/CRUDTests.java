package org.shark.boot12;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.shark.boot12.common.util.JpaUtil;
import org.shark.boot12.user.entity.AccessLog;
import org.shark.boot12.user.entity.User;
import org.shark.boot12.user.enums.Gender;
import org.shark.boot12.user.enums.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class CRUDTests {

  @Autowired
  private JpaUtil jpaUtil;
  private EntityManager em;
  
  //전체 테스트 시작 전 : 엔티티 매니저 팩토리 생성
  @BeforeAll
  void setUpBeforeClass() {
    jpaUtil.initFactory();
  }
  
  //각 테스트 시작 전 : 엔티티 매니저 생성
  @BeforeEach
  void setUp() {
    em = jpaUtil.getEntityManager();
  }
  
  //각 테스트 종료 후 : 엔티티 매니저 소멸
  @AfterEach
  void tearDown() {
    if (em == null && em.isOpen())
      em.clear();
  }
  
  //전체 테스트 종료 후 : 엔티티 매니저 팩토리 소멸
  @AfterAll
  void tearDownAfterClass() {
    jpaUtil.closeFactory();
  }
	
  @Test
  @DisplayName("User 엔티티 등록하기")
	void createUserTest() {
    //User 엔티티 생성
    User user = User.createUser("김삼번", "number@exaple.com", Gender.MALE);

    //트랜잭션 시작
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      //엔티티를 영속성 컨텍스트(Persistence Context)에 저장
      em.persist(user);
      log.info("after persist()");
      
      //트랜잭션 커밋
      tx.commit();
      log.info("after commit()");
      
    } catch (Exception e) {
      //트랜잭션 롤백
      tx.rollback();
      throw e;
    }
    
    Assertions.assertNotNull(user.getId());
    Assertions.assertNotNull(user.getCreatedAt());
    
	}
  
  /*
   *  Hibernate: 
      insert 
      into
          users
          (created_at, email, gender, username, withraw_yn) 
      values
          (?, ?, ?, ?, ?)
      2025-09-25T10:25:40.894+09:00  INFO 1916 --- [12_Persistence_Context] [           main] org.shark.boot12.CRUDTests               : after persist()
      2025-09-25T10:25:40.900+09:00  INFO 1916 --- [12_Persistence_Context] [           main] org.shark.boot12.CRUDTests               : after commit()

   * 실행 순서 정리
   * 1. insert into
   * 2. after persist()
   * 3. after commit()
   * 
   * 실행 순서 이유
   * 영속성 컨텍스트에 엔티티를 저장하기 위해서는 반드시 엔티티에 ID가 필요합니다.
   * User 엔티티는 AUTO INCEMENT 전략을 사용하므로 INSERT 쿼리를 실행해야만 ID를 알 수 있습니다.
   * 따라서 영속성 컨텍스트에 엔티티를 저장하는 persist() 호출 시 곧바로 DB INSERT 퀴리가 실행됩니다.
   * DB에 저장되는건 COMMIT을 해야 합니다.
   */
  
  @Test
  @DisplayName("AccessLog 엔티티 등록하기")
  void createAccessLogTest() {
    //AccessLog 엔티티 생성
    AccessLog accessLog = AccessLog.createAccessLog("USER_LOGIN", "사용자 로그인 성공", LogLevel.INFO, "192.168.100.5", "Mozilla/5.0");
    
    //트랜잭션 시작
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      //AccessLog 엔티티를 영속성 컨텍스트에 저장
      em.persist(accessLog);
      log.info("after persist()");
      
      //트랜잭션 커밋
      tx.commit();
      log.info("after commit()");
      
    } catch (Exception e) {
      //트랜잭션 롤백
      tx.rollback();
      throw e;
    }
    
    Assertions.assertNotNull(accessLog.getId());
    Assertions.assertNotNull(accessLog.getCreatedAt());
    
  }
  
  /*
   *  Hibernate: 
      create table access_log (
          access_id bigint not null,
          created_at datetime(6),
          ip varchar(255),
          log_level tinyint check (log_level between 0 and 1),
          log_message varchar(255),
          log_type varchar(255),
          user_agent varchar(255),
          primary key (access_id)
      ) engine=InnoDB
      Hibernate: 
          create table access_log_seq (
              entity varchar(255) not null,
              nextVal bigint,
              primary key (entity)
          ) engine=InnoDB
      Hibernate: 
          insert into access_log_seq(entity, nextVal) values ('accessLog',0)
      Hibernate: 
      select
          tbl.nextVal 
      from
          access_log_seq tbl 
      where
          tbl.entity=? for update
      Hibernate: 
          update
              access_log_seq 
          set
              nextVal=?  
          where
              nextVal=? 
              and entity=?
      2025-09-25T11:09:07.694+09:00  INFO 1640 --- [12_Persistence_Context] [           main] org.shark.boot12.CRUDTests               : after persist()
      Hibernate: 
          insert 
          into
              access_log
              (created_at, ip, log_level, log_message, log_type, user_agent, access_id) 
          values
              (?, ?, ?, ?, ?, ?, ?)
      2025-09-25T11:09:07.710+09:00  INFO 1640 --- [12_Persistence_Context] [           main] org.shark.boot12.CRUDTests               : after commit()
   * 
   * 실행 순서 정리
   * 1. insert into
   * 2. after persist()
   * 3. after commit()
   * 
   * 실행 순서 이유
   * 영속성 컨텍스트에 엔티티를 저장하기 위해서는 반드시 엔티티에 ID가 필요합니다.
   * AccessLog 엔티티는 access_log_seq 테이블로부터 ID를 받아서 사용합니다.
   * 따라서 AccessLog 엔티티를 DB에 INSERT 하지 않아도 엔티티의 ID를 알 수 있으므로 persist() 메소드 호출 시 INSERT 쿼리가 동작하지 않습니다.
   */
  
  @Test
  @DisplayName("User 엔티티 조회하기")
  void findUserTest() {
    //엔티티를 조회할 땐 ID를 이용해서 조회합니다.
    Long id = 1L;
    
    /*
     * 엔티티 매니저를 이용한 엔티티 조회 원리
     * 1. find() 호출 시 영속성 컨텍스트에서 엔티티를 조회합니다.
     * 2. 없으면 DB에서 조회합니다.
     * 3. 그래도 없으면 null을 반환합니다.
     * 4. 조회한 엔티티는 영속성 컨텍스트에서 관리합니다. (find() 호출 결과 엔티티는 영속성 컨텍스트에 저장됩니다.)
     */
    User foundUser = em.find(User.class, id);
    
    Assertions.assertNotNull(foundUser);
    
  }
  
  @Test
  @DisplayName("AccessLog 엔티티 조회하기")
  void findAccessLogTest() {
    Long id = 1L;
    AccessLog foundAccessLog = em.find(AccessLog.class, id);
    Assertions.assertNotNull(foundAccessLog);
  }
  
  @Test
  @DisplayName("User 엔티티 삭제하기")
  void deleteUserTest() {
    Long id = 1L;
    
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      //삭제할 엔티티를 조회하여 영속성 컨텍스트에 저장합니다.
      User foundUser = em.find(User.class, id);
      
      //삭제 예정 상태로 엔티티를 변경합니다.
      em.remove(foundUser);
      
      //실제 삭제
      tx.commit();
      
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
    
    Assertions.assertNull(em.find(User.class, id));   //삭제 확인
    
  }
  
  @Test
  @DisplayName("AccessLog 엔티티 삭제하기")
  void deleteAccessLogTest() {
    Long id = 1L;
    
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      AccessLog foundAccessLog = em.find(AccessLog.class, id);   //DB에서 AccessLog 엔티티를 조회해 영속성 컨텍스트에 저장합니다.
      em.remove(foundAccessLog);   //AccessLog 엔티티를 영속성 컨텍스트에서 제거하고 삭제 예정 상태로 바꿉니다.
      em.persist(foundAccessLog);   //삭제 예정 상태의 AccessLog 엔티티를 영속성 컨텍스트에 다시 저장합니다.
      tx.commit();   //영속성 컨텍스트에 여전히 AccessLog 엔티티가 저장되어 있으므로 아무런 변화가 없습니다.
      
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
    
    Assertions.assertNull(em.find(AccessLog.class, id));   //따라서 null이 아니므로 테스트 실패
    
  }
  
  @Test
  @DisplayName("User 엔티티 수정하기")
  void updateUserTest() {
    
    Long id = 1L;
    String username = "이삼번";
    Gender gender = Gender.FEMALE;
    
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      //수정할 엔티티를 조회해서 영속성 컨텍스트에 저장합니다.
      User foundUser = em.find(User.class, id);
      
      //영속성 컨텍스트에 저장된 엔티티 수정
      foundUser.setUsername(username);
      foundUser.setGender(gender);
      
      //트랜잭션 커밋
      tx.commit();
      
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
    
    Assertions.assertEquals(username, em.find(User.class, id).getUsername());
    
  }
 
}


