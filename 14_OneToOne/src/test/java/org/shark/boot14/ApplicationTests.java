package org.shark.boot14;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.shark.boot14.common.util.JpaUtil;
import org.shark.boot14.member.entity.Locker;
import org.shark.boot14.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class ApplicationTests {

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
	@DisplayName("1:1 연관관계 저장 테스트")
	void createOneToOneRelationshipTest() {
	  Member member = Member.createMember("홍길동", "010-111-1111");
	  Locker locker = Locker.createdLocker("B1");
	  member.assignLocker(locker);   //연관관계가 설정되는 코드
	  
	  EntityTransaction tx = em.getTransaction();
	  tx.begin();
	  
	  try {
      em.persist(member);
      em.flush();
      em.clear();
      
      Long id = member.getId();
      Member foundMember = em.find(Member.class, id);   //SELECT 쿼리 실행
                                                        //fetch=FetchType.EAGER : Member와 연관된 Locker 모두 조회 (join 발생)
                                                        //fetch=FetchType.LAZY : Member만 조회
      log.info("Member's name: {}", member.getName());
      
      Locker foundLocker = foundMember.getLocker();   //fetch=FetchType.LAZY : Locker를 조회
      String location = foundLocker.getLocation();
      log.info("Locker's location: {}", location);
      
      tx.commit();
      
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
	  
	}
	
	@Test
  @DisplayName("양방향 매핑 테스트")
	void biDirectionMappingTest() {
	  //자식 엔티티 Locker를 이용하여 부모 엔티티 Member의 정보를 조회하기 위해서는 양방향 매핑이 필요합니다.
	  Member member = Member.createMember("아카자", "333-333-333");
	  Locker locker = Locker.createdLocker("B3");
	  member.assignLocker(locker);
	  
	  EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      em.persist(member);
      em.flush();
      em.clear();
      
      Long locker_id = locker.getId();
      Locker foundLocker = em.find(Locker.class, locker_id);
      
      //양방향 매핑 설정 후 @ToString 활용 시 Member와 Locker간 순환 참조가 발생하므로
      //toString() 메소드를 직접 오버라이드 한 뒤 순환 참조 발생을 막도록 코드를 수정해야 합니다.
      log.info("foundLocker: {}", foundLocker);
      
      //foundLocker를 이용한 Member 조회
      //양방향 매핑 설정 시 사용 가능
      Member foundMember = foundLocker.getMember();
      log.info("{}", foundMember.getName());
      
      tx.commit();
      
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
	  
	}

}
