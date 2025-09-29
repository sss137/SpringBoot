package org.shark.boot16;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.shark.boot16.common.util.JpaUtil;
import org.shark.boot16.product.entity.Contact;
import org.shark.boot16.product.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class ApplicationTests2 {

  @Autowired
  private JpaUtil jpaUtil;
  
  private EntityManager em;
  
  //----- 전체 테스트 시작 전 : 엔티티 매니저 팩토리 생성
  @BeforeAll
  void setUpBeforeClass() {
    jpaUtil.initFactory();
  }
  
  //----- 각 테스트 시작 전 : 엔티티 매니저 생성
  @BeforeEach
  void setUp() {
    em = jpaUtil.getEntityManager();
  }
  
  //----- 각 테스트 종료 후 : 엔티티 매니저 소멸
  @AfterEach
  void tearDown() {
    if (em != null && em.isOpen()) {
      em.close();
    }
  }
  
  //----- 전체 테스트 종료 후 : 엔티티 매니저 팩토리 소멸
  @AfterAll
  void tearDownAfterClass() {
    jpaUtil.closeFactory();
  }
  
	@Test
	@DisplayName("엔티티 조회 테스트")
	void entityQueryTest() {
	  // SELECT 절에서 엔티티를 조회하는 방식입니다.
	  // SELECT 결과를 영속 컨텍스트(Persistence Context)에 저장합니다.
	  String jpql = "SELECT u FROM User u WHERE u.userId = 1";
	  User foundUser = em.createQuery(jpql, User.class).getSingleResult();
	  // 영속 컨텍스트에 존재하고 있는지 확인하기
	  Assertions.assertTrue(em.contains(foundUser));
	  // Dirty Checking 테스트하기
	  // : 영속 엔티티의 정보를 수정한 뒤, 트랜잭션 커밋을 수행하면 엔티티의 변경사항이 감지되어 데이터베이스에 적용됩니다.
	  EntityTransaction tx = em.getTransaction();
	  tx.begin();
	  foundUser.setUsername("최철수");  // 기존 "김철수"
	  tx.commit();
	  em.clear();  // 영속 컨텍스트 내 모든 엔티티를 준영속 엔티티로 변경
	  Assertions.assertEquals("최철수", em.find(User.class, 1).getUsername());
	}
	
	@Test
	@DisplayName("스칼라 조회 테스트")
	void scalaQueryTest() {
	  // SELECT 절에서 숫자, 문자 등 특정 데이터 타입을 조회합니다.
	  // 스칼라 조회 결과는 영속 컨텍스트에 저장되지 않습니다.
	  
	  // userId가 1인 사용자의 이름을 조회하고, 조회 결과가 영속 컨텍스트에서 관리되는지 확인하세요.
	  Integer userId = 1;
	  String jpql = "SELECT u.username FROM User u WHERE u.userId = :userId";
	  String username = em.createQuery(jpql, String.class)
	                      .setParameter("userId", userId)
	                      .getSingleResult();
	  Assertions.assertTrue(em.contains(username));  // 테스트 실패
	}
	
	@Test
  @DisplayName("Embeddable 조회 테스트")
  void embeddableQueryTest() {
    // 엔티티가 @Embedded 한 객체를 조회합니다.
	  // @Embedded 객체(@Embeddable) 조회 결과는 영속 컨텍스트에 저장되지 않습니다.
	  
	  String jpql = "SELECT u.contact FROM User u WHERE u.userId = 1";
	  Contact contact = em.createQuery(jpql, Contact.class).getSingleResult();
	  Assertions.assertTrue(em.contains(contact));
  }
  
}