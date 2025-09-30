package org.shark.boot16.product.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/*
 * Order : 각 주문은 여러 결제 수단을 가질 수 있습니다.
 * Payment : 각 결제 수단은 여러 주문에서 사용될 수 있습니다.
 */
@Entity
@Table(name = "order_payment")
@Getter
@Setter
public class OrderPayment {
  @EmbeddedId   //복합키 클래스(@EmbeddedId 객체)를 ID로 설정
  private OrderPaymentId id;
 
  //복합키의 orderId 필드와 연관관계 필드 Order order 엔티티를 매핑하는 작업
  //연관관계를 맺는 Order order 엔티티의 ID 값이 복합키의 orderId 필드에 자동으로 동기화
  @MapsId("orderId")   //복합키의 orderId 필드를 의미
  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;
  
  @MapsId("paymentId")   //복합키의 paymentId 필드를 의미
  @ManyToOne
  @JoinColumn(name = "payment_id")
  private Payment payment;
  
  protected OrderPayment() {}
  
  public static OrderPayment createOrderPayment(Order order, Payment payment) {
    OrderPayment orderPayment = new OrderPayment();
    orderPayment.order = order;
    orderPayment.payment = payment;
    return orderPayment;
  }
  
}


