package org.shark.boot11.review;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_review")   //생성되는 테이블명 변경
public class Review {

  /*
   * 식별자 설정 2 : @GeneratedValue(strategy = GenerationType.IDENTITY)
   * - 식별 칼럼에 매핑하는 방법입니다. (예시 : MySQL 자동 증가 칼럼이 대표적)
   * - DB에서 식별값을 생성하므로 엔티티 생성 시 식별값 생성이 없도록 처리
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long rid;
  
  private String content;

  public Review(String content) {
    super();
    this.content = content;
  }

  @Override
  public String toString() {
    return "Review [rid=" + rid + ", content=" + content + "]";
  }
  
}


