package org.shark.boot16.product.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

/*
 * 복합키로 사용할 클래스
 *   1. 기본 생성자
 *   2. equals, hashCode 오버라이드 (객체 간 값의 비교를 위해서 반드시 필요)
 *   3. 직렬화 (Serializable 인터페이스 구현)
 *   4. 복합키를 사용할 엔티티에 포함되도록 @Embeddable 처리
 */
@Embeddable
@EqualsAndHashCode
public class OrderProductId implements Serializable {
  @Column(name = "order_id")
  private Integer orderId;
  
  @Column(name = "product_id")
  private Integer productId;

  protected OrderProductId() {}

  public OrderProductId createOrderProductId(Integer orderId, Integer productId) {
    OrderProductId orderProductId = new OrderProductId();
    orderProductId.orderId = orderId;
    orderProductId.productId = productId;
    return orderProductId;
  }
  
}
