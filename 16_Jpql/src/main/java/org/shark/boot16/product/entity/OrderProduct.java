package org.shark.boot16.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_product")

@Getter
@Setter
public class OrderProduct {

  // 복합키 @Embeddable 객체를 OrderProduct 엔티티의 Id로 사용
  @EmbeddedId
  private OrderProductId id;

  // 복합키의 orderId 필드와 연관관계 필드 Order order 엔티티를 매핑
  // Order order 엔티티의 식별자(orderId) 값이 복합키의 orderId에 자동으로 동기화
  @MapsId("orderId")  //----- 복합키의 orderId를 의미
  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;

  // 복합키의 productId 필드와 연관관계 필드 Product product 엔티티를 매핑
  // Product product 엔티티의 식별자(productId) 값이 복합키의 productId에 자동으로 동기화
  @MapsId("productId")  //----- 복합키의 productId를 의미
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @Column(name = "order_quantity", nullable = false)
  private Integer orderQuantity;

  @Column(name = "unit_price", nullable = false)
  private Integer unitPrice;

  protected OrderProduct() {}

  public static OrderProduct createOrderProduct(Order order, Product product, Integer orderQuantity, Integer unitPrice) {
    OrderProduct op = new OrderProduct();
    op.setOrder(order);
    op.setProduct(product);
    op.setOrderQuantity(orderQuantity);
    op.setUnitPrice(unitPrice);
    return op;
  }

  @Override
  public String toString() {
    return "OrderProduct [id=" + id + ", order=" + order + ", product=" + product + ", orderQuantity=" + orderQuantity
        + ", unitPrice=" + unitPrice + "]";
  }
    
}