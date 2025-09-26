package org.shark.boot13;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.shark.boot13.common.embeddable.Address;
import org.shark.boot13.common.embeddable.Contact;
import org.shark.boot13.common.embeddable.Name;
import org.shark.boot13.common.util.JpaUtil;
import org.shark.boot13.company.entity.Company;
import org.shark.boot13.company.entity.Employee;
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
	@DisplayName("Employee 엔티티 저장하기")
	void createEmployeeTest() {
	  Name name = Name.createName("길동", "홍");
	  Address address = Address.createAddress("12345", "서울", "강남대로 123");
	  Employee employee = Employee.createEmployee(name, address);
	  
	  EntityTransaction tx = em.getTransaction();
	  tx.begin();
	  
	  try {
      em.persist(employee);
      em.flush();
      
      assertNotNull(employee.getId());
      
      em.clear();
      
      Employee foundEmployee = em.find(Employee.class, employee.getId());
      
      assertNotNull(foundEmployee);
      
      tx.commit();
      
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
	  
	}
	
	@Test
  @DisplayName("Company 엔티티 등록하기")
	void createCompanyTest() {
	  Contact contact = Contact.createContact("ad@ex.com", "02.111.1111", "02.222.2222");
	  Address address = Address.createAddress("02345", "seoul", "강남 123");
	  Company company = Company.createCompany("HelloIT", contact, address);
	  
	  EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      em.persist(company);
      
      assertNotNull(company.getId());
      
      Company foundCompany = em.find(Company.class, company.getId());
      
      assertSame(company, foundCompany);
      
      foundCompany.getAddress().setCity("인천");
      foundCompany.getAddress().setStreetAddr("인천 123");
      
      em.flush();
      
      tx.commit();
      
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
	  
	}

}


