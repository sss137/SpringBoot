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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class LifeCycleTests {

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
  @DisplayName("영속 엔티티 점검")
	void transientTest() {
    /*
     * 영속 엔티티 (Managed Entity)가 되는 방법
     * 1. persist()
     * 2. find()
     * 3. JPQL
     * 4. merge()
     */
    
    //User 엔티티 생성
    User user = User.createUser("홍길동", "hong@exaple.com", Gender.MALE);
    
    //persist()
    em.persist(user);
    
    //AccessLog 엔티티 조회 : find()
    AccessLog accessLog = em.find(AccessLog.class, 1L);
    
    //영속성 컨텍스트에 저장되어 있는지 확인
    Assertions.assertTrue(em.contains(user));
    Assertions.assertTrue(em.contains(accessLog));
    
	}
  
  @Test
  @DisplayName("준영속 엔티티 detach() 테스트")
  void detachTest() {
    /*
     * 준영속 엔티티
     * 1. 영속성 컨텍스트에서 관리하던 엔티티가 영속성 컨텍스트에서 분리된 상태입니다.
     * 2. 비영속 엔티티와의 가장 큰 차이점은 식별자(ID) 보유 여부입니다. (ID가 있으면 준영속, 없으면 비영속입니다.)
     */
    
    //비영속 엔티티 만들기
    User user = User.createUser("아카자", "akaza@exaple.com", Gender.MALE);
    
    //트랜잭션 시작
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      //비영속 -> 영속 엔티티로 바꾸기
      em.persist(user);
      
      //Dirty Checking
      user.setUsername("상현3");
      user.setEmail("akz@example.com");
      
      //영속 -> 준영속 엔티티로 바꾸기
      //준영속 엔티티는 Dirty Checking이 동작하지 않습니다.
      //오직 영속 엔티티만 Dirty Checking 동작합니다.
      em.detach(user);
      
      //트랜잭션 커밋
      tx.commit();
      
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
    
    Assertions.assertEquals("상현3", em.find(User.class, 1L).getUsername());
    
  }
  
  @Test
  @DisplayName("준영속 엔티티 clear() 테스트")
  void clearTest() {
    //clear() 메소드는 모든 영속 엔티티를 준영속 엔티티로 바꿉니다.
    User user = User.createUser("아카자", "akaza@exaple.com", Gender.MALE);
    
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      em.persist(user);   //user 엔티티를 영속성 컨텍스트에 저장
      em.flush();   //INSERT 쿼리 실행
      em.find(User.class, 1);   //영속성 컨텍스트에서 ID가 1인 User 엔티티 조회 : 성공
      em.find(User.class, 1);   //영속성 컨텍스트에서 ID가 1인 User 엔티티 조회 : 성공
      em.clear();   //영속성 컨텍스트에 있는 모든 엔티티를 준영속 상태로 변경 (영속성 컨텍스트에는 아무 엔티티도 없음)
      em.find(User.class, 1);   //영속성 컨텍스트에서 ID가 1인 User 엔티티 조회 : 실패 (DB로 SELECT 쿼리를 날림)
      
      tx.commit();
      
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
    
  }
  
  @Test
  @DisplayName("준영속 엔티티 merge() 테스트")
  void mergeTest() {
    /*
     * merge()
     * 1. 준영속 엔티티를 영속성 컨텍스트에 반환합니다.
     * 2. merge() 동작
     *   1) 준영속 엔티티를 merge()에 전달합니다.
     *   2) 전달 받은 준영속 엔티티의 ID를 이용해 영속성 컨텍스트의 1차 캐시에서 엔티티를 조회합니다.
     *     (1) 1차 캐시에 엔티티가 있으면 준영속 엔티티의 모든 필드값을 1차 캐시에 있는 영속 엔티티에 덮어쓰기합니다.
     *     (2) 1차 캐시에 엔티티가 없으면 DB에서 조회해서 1차 캐시에 저장한 뒤 준영속 엔티티의 모든 필드값을 덮어쓰기합니다.
     *   3) 새로운 영속 엔티티를 반환합니다.
     */
    User user = User.createUser("아카자", "akaza@exaple.com", Gender.MALE);
    
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      em.persist(user);   //user 엔티티를 영속성 컨텍스트에 저장
      em.flush();   //INSERT 쿼리 실행
      
      em.detach(user);   //user 엔티티를 준영속 상태로 변경 (1차 캐시에서 user 엔티티 제거)
      
      User mergedUser = em.merge(user);   //1차 캐시에서 user 엔티티의 ID를 가진 엔티티를 찾지만 없기 때문에 DB에서 SELECT해서 가져옵니다.
      User mergedUser2 = em.merge(user);  //1차 캐시에서 user 엔티티의 ID를 가진 엔티티를 찾습니다.
      
      Assertions.assertTrue(em.contains(mergedUser));   //merge()가 반환한 엔티티는 영속 엔티티입니다.
      Assertions.assertTrue(em.contains(mergedUser2));  //merge()가 반환한 엔티티는 영속 엔티티입니다.
      
      //Assertions.assertTrue(em.contains(user));   //merge() 이후에도 준영속 엔티티는 여전히 그대로입니다.
      
      tx.commit();
      
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
    
  }
  
  @Test
  @DisplayName("준영속 엔티티  merge()와 Dirty Checking 테스트")
  void mergeDirtyCheckingTest() {
    User user = User.createUser("아카자", "akaza@exaple.com", Gender.MALE);
    
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      em.persist(user);
      em.flush();
      
      em.detach(user);
      
      user.setUsername("상현3");
      user.setEmail("akz@example.com");   //영속성 컨텍스트를 수정한 것이 아니기 때문에 엄밀히 Dirty Checking은 아닙니다.
      
      User mergedUser = em.merge(user);
      
      log.info("{}", mergedUser);
      
      tx.commit();
      
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
    
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
    
    Long id = 3L;
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


