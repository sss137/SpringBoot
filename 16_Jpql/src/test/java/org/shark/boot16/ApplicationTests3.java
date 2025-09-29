package org.shark.boot16;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.shark.boot16.common.util.JpaUtil;
import org.shark.boot16.product.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class ApplicationTests3 {

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
	@DisplayName("내부 조인 테스트")
	void innerJoinTest() {
	  String jpql = "SELECT p FROM Product p JOIN p.category c";
	  List<Product> products = em.createQuery(jpql, Product.class)
	                             .getResultList();
	  products.stream().forEach(product -> System.out.println(product));
	  // products.stream().forEach(System.out::println);
	}
	
	@Test
	@DisplayName("내부 조인(페치 조인) 테스트")
	void fetchJoinTest() {
	  // N + 1 문제 (일반 내부 조인 실행 시 문제점)
	  // 1. 1번 : 최초 내부 조인 쿼리문이 실행됩니다. 이 때는 Product 정보만 조회됩니다.
	  // 2. N번 : 각 Product 엔티티의 Category 필드를 조회할 때 별도의 쿼리문으로 Category 정보를 조회합니다.
	  
	  // N + 1 문제 해결
	  // 1. 최초 내부 조인 쿼리문이 실행될 때 Product, Category 모두 조회되도록 합니다.
	  // 2. 그러기 위해서 JPQL에서 JOIN FETCH를 사용합니다.
	  
	  String jpql = "SELECT p FROM Product p JOIN FETCH p.category c";
	  List<Product> products = em.createQuery(jpql, Product.class)
	      .getResultList();
	  products.stream().forEach(System.out::println);
	}



	
  
}