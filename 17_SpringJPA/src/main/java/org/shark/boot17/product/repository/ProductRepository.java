package org.shark.boot17.product.repository;

import org.shark.boot17.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Spring Data JPA - Repository 인터페이스 상속 구조
 * 
 *   Repository  ------------------ 별도 기능 없음
 *       ↑
 *   CrudRepository  -------------- CRUD 기능 제공
 *       ↑
 *   PagingAndSortingRepository  -- 페이징 기능 제공
 *       ↑
 *   JpaRepository  --------------- 영속 컨텍스트 관련 일부 JPA 기능 제공
 */

/*
 * 쿼리 메소드 (Query Method)
 * 
 * 1. JPQL을 메소드로 대신 처리할 수 있도록 제공하는 기능입니다.
 * 2. 메소드의 이름을 이용해서 JPQL을 생성하고 조회합니다.
 * 3. 메소드의 이름은 "find + 엔티티명 + By + 필드명 + 키워드" 규칙을 사용합니다. (엔티티명은 생략 가능합니다.)
 * 4. 쿼리 메소드 유형
 *      -----------------+-----------------------------+---------------------------------------
 *      KEYWORD          | METHOD                      |JPQL
 *      -----------------+-----------------------------+---------------------------------------
 *   1) And              | findByCodeAndName           | WHERE m.code = ?1 AND m.name = ?2
 *   2) Or               | findByCodeOrName            | WHERE m.code = ?1 OR m.name = ?2
 *   3) Not              | findByNameNot               | WHERE m.name <> ?1
 *   4) Between          | findByPriceBetween          | WHERE m.price BETWEEN 1? AND ?2
 *   5) LessThan         | findByPriceLessThan         | WHERE m.price < ?1
 *   6) LessThanEqual    | findByPriceLessThanEqual    | WHERE m.price < ?1
 *   7) GreaterThan      | findByPriceGreaterThan      | WHERE m.price > ?1
 *   8) GreaterThanEqual | findByPriceGreaterThanEqual | WHERE m.price >= ?1
 *   9) IsNull           | findByNameIsNull            | WHERE m.name IS NULL
 *  10) (Is)NotNull      | findByNameIsNotNull         | WHERE m.name IS NOT NULL
 *  11) Like             | findByNameLike              | WHERE m.name LIKE ?1
 *  12) StartingWith     | findByNameStartingWith      | WHERE m.name LIKE ?1 || '%'
 *  13) EndingWith       | findByNameEndingWith        | WHERE m.name LIKE '%' || ?1
 *  14) Containing       | findByNameContaining        | WHERE m.name LIKE '%' || ?1 || '%'
 *  15) OrderBy          | findByNameOrderByCodeDesc   | WHERE m.name = ?1 ORDER BY m.code DESC
 *      -----------------+-----------------------------+---------------------------------------
 */

public interface ProductRepository extends JpaRepository<Product, Integer> {
  
  // 카테고리별 조회
  Page<Product> findByCategoryCategoryId(Integer categoryId, Pageable pageable);
  
}


