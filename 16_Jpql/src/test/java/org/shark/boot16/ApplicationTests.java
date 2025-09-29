package org.shark.boot16;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.shark.boot16.common.util.JpaUtil;
import org.shark.boot16.product.entity.AccessLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class ApplicationTests {

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
	@DisplayName("단일 행 조회 테스트 - TypedQuery")
	void singleRowTypedQueryTest() {
	  String jpql = "SELECT al.logType FROM AccessLog AS al WHERE al.logId = 1";  // AS 절 생략 가능
	  TypedQuery<String> typedQuery = em.createQuery(jpql, String.class);
	  String logType = typedQuery.getSingleResult();
	  log.info("결과: {}", logType);
	}
	
	@Test
	@DisplayName("단일 행 조회 테스트 - Query")
	void singleRowQueryTest() {
	  String jpql = "SELECT al.logType FROM AccessLog al WHERE al.logId = 1";
	  Query query = em.createQuery(jpql);
	  Object obj = query.getSingleResult();
	  String logType = (String) obj;
	  log.info("결과: {}", logType);
	}
	
	@Test
	@DisplayName("다중 행 조회 테스트 - TypedQuery")
	void multiRowTypedQueryTest() {
	  String jpql = "SELECT al FROM AccessLog al";
	  TypedQuery<AccessLog> typedQuery = em.createQuery(jpql, AccessLog.class);
	  List<AccessLog> accessLogs = typedQuery.getResultList();
	  accessLogs.stream().forEach(accessLog -> log.info("{}", accessLog));
	}
	
	@SuppressWarnings("unchecked")
  @Test
  @DisplayName("다중 행 조회 테스트 - Query")
  void multiRowQueryTest() {
    String jpql = "SELECT al FROM AccessLog al";
    Query query = em.createQuery(jpql);
    List<AccessLog> accessLogs = query.getResultList();
    accessLogs.stream().forEach(accessLog -> log.info("{}", accessLog));
  }

	@Test
	@DisplayName("파라미터 바인딩 테스트 - 이름 기준")
	void parameterBindingNameTest() {
	  // JPQL 작성 시 ":파라미터" 형태로 사용합니다.
	  // setParameter 메소드로 값을 바인딩합니다.
	  String logLevel = "INFO";
	  String jpql = "SELECT al FROM AccessLog al WHERE al.logLevel = :logLevel";
	  TypedQuery<AccessLog> typedQuery = em.createQuery(jpql, AccessLog.class)
	                                       .setParameter("logLevel", logLevel);
	  Stream<AccessLog> stream = typedQuery.getResultStream();
	  // List<AccessLog> accessLogs = stream.collect(Collectors.toList());
	  stream.forEach(accessLog -> log.info("{}", accessLog));
	}
	
	@Test
	@DisplayName("파라미터 바인딩 테스트 - 위치 기준")
	void parameterBindingPositionTest() {
	  // JPQL 작성 시 "?숫자" 형태로 사용합니다.
	  // 숫자는 위치 정보를 의미하고, 1부터 시작합니다.
	  // setParameter 메소드로 값을 바인딩합니다.
	  String logLevel = "INFO";
	  Integer logId = 5;
	  String jpql = "SELECT al FROM AccessLog al WHERE al.logId >= ?2 AND al.logLevel = ?1";
	  TypedQuery<AccessLog> typedQuery = em.createQuery(jpql, AccessLog.class)
	                                       .setParameter(1, logLevel)
	                                       .setParameter(2, logId);
	  List<AccessLog> accessLogs = typedQuery.getResultList();
	  accessLogs.stream().forEach(accessLog -> log.info("{}", accessLog));
	}
	
	// 1. logLevel의 중복을 제거하여 조회하세요.
	@Test
  @DisplayName("중복 제거 조회 테스트")
  void distinctTest() {
    String jpql = "SELECT DISTINCT al.logLevel FROM AccessLog al";
    TypedQuery<String> typedQuery = em.createQuery(jpql, String.class);
    Stream<String> stream = typedQuery.getResultStream();
    stream.forEach(accessLog -> log.info("{}", accessLog));
  }
  
	// 2. logId가 1에서 5인 엔티티를 조회하세요.
  @Test
  @DisplayName("BETWEEN AND 연산자 테스트")
  void betweenAndOperatorTest() {
    Integer begin = 1, end = 5;
    String jpql = "SELECT al FROM AccessLog al WHERE al.logId BETWEEN :begin AND :end";
    TypedQuery<AccessLog> typedQuery = em.createQuery(jpql, AccessLog.class)
                                         .setParameter("begin", begin)
                                         .setParameter("end", end);
    Stream<AccessLog> stream = typedQuery.getResultStream();
    stream.forEach(accessLog -> log.info("{}", accessLog));
  }
  
  // 3. logId가 1 또는 5인 엔티티를 조회하세요.
  @Test
  @DisplayName("IN 연산자 테스트")
  void inOperatorTest() {
    Integer num1 = 1, num2 = 5;
    String jpql = "SELECT al FROM AccessLog al WHERE al.logId IN(:a, :b)";
    TypedQuery<AccessLog> typedQuery = em.createQuery(jpql, AccessLog.class)
                                         .setParameter("a", num1)
                                         .setParameter("b", num2);
    Stream<AccessLog> stream = typedQuery.getResultStream();
    stream.forEach(accessLog -> log.info("{}", accessLog));
  }
  
  // 4. logType에 "USER"가 포함된 엔티티를 조회하세요.
  @Test
  @DisplayName("LIKE 연산자 테스트")
  void likeOperatorTest() {
    String q = "USER";
    String jpql = "SELECT al FROM AccessLog al WHERE al.logType LIKE CONCAT('%', :q, '%')";
    TypedQuery<AccessLog> typedQuery = em.createQuery(jpql, AccessLog.class)
                                         .setParameter("q", q);
    Stream<AccessLog> stream = typedQuery.getResultStream();
    stream.forEach(accessLog -> log.info("{}", accessLog));
  }
	
	@Test
	@DisplayName("페이징 API 테스트")
	void pagingTest() {
	  Integer offset = 0;
	  Integer size = 5;
	  String sort = "DESC";
	  String jpql = "SELECT al FROM AccessLog al ORDER BY al.logId " + sort;
	  TypedQuery<AccessLog> typedQuery = em.createQuery(jpql, AccessLog.class)
	                                       .setFirstResult(offset)
	                                       .setMaxResults(size);
    Stream<AccessLog> stream = typedQuery.getResultStream();
    stream.forEach(accessLog -> log.info("{}", accessLog));
	}
  
  @Test
  @DisplayName("통계함수 테스트")
  void groupFunctionTest() {
    // COUNT 함수
    // 1. 반환 타입이 Long 입니다.
    // 2. 데이터가 없는 경우 0을 반환합니다. (반환 타입을 long 으로 설정해도 됩니다.)
    // 3. COUNT 함수 외 통계함수(예: SUM)는 데이터가 없는 경우 null을 반환합니다.
    String jpql = "SELECT COUNT(al.logId) FROM AccessLog al WHERE al.logId = 0";
    Long count = em.createQuery(jpql, Long.class).getSingleResult();
    log.info("count:{}", count);
    
    String jpql2 = "SELECT SUM(al.logId) FROM AccessLog al WHERE al.logId = 0";
    Long sum = em.createQuery(jpql2, Long.class).getSingleResult();
    log.info("sum:{}", sum);
    
    // COALESCE 함수 (코엘러스)
    // 1. SQL 표준 함수입니다. (JPQL 지원 가능)
    // 2. 전달된 인자들을 순서대로 조회하여 null이 아닌 첫 번째 값을 반환합니다.
    // 3. 모든 값이 null이면 null을 반환합니다.
    String jpql3 = "SELECT COALESCE(SUM(al.logId), 0) FROM AccessLog al WHERE al.logId = 0";
    Long sum2 = em.createQuery(jpql3, Long.class).getSingleResult();
    log.info("sum2:{}", sum2);
    
    String jpql4 = "SELECT AVG(al.logId) FROM AccessLog al";
    Double avg = em.createQuery(jpql4, Double.class).getSingleResult();
    log.info("avg:{}", avg);

    String jpql5 = "SELECT MAX(al.logId) FROM AccessLog al";
    Integer max = em.createQuery(jpql5, Integer.class).getSingleResult();
    log.info("max:{}", max);
  }
  
  @Test
  @DisplayName("그룹화 테스트")
  void groupByTest() {
    Long groupCount = 2L;
    String jpql = "SELECT al.logLevel, COUNT(al.logId) FROM AccessLog al GROUP BY al.logLevel HAVING COUNT(al.logId) >= :groupCount";
    List<Object[]> accessLogs = em.createQuery(jpql, Object[].class)
                                  .setParameter("groupCount", groupCount)
                                  .getResultList();
    accessLogs.stream().forEach(objArr -> log.info("{}", Arrays.toString(objArr)));
  }
  
}