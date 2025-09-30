package org.shark.boot16.product.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Integer orderId;

  @Column(name = "order_date", nullable = false)
  private LocalDate orderDate;

  @Column(name = "order_time", nullable = false)
  private LocalTime orderTime;

  @Column(name = "total_order_amount", nullable = false)
  private Integer totalOrderAmount;

  @Column(name = "order_status", nullable = false, length = 20)
  private String orderStatus = "RECEIVED";

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "order")
  private List<OrderPayment> orderPayments;
  
  @OneToMany(mappedBy = "order")
  private List<OrderProduct> orderProducts;

  protected Order() {}

  public static Order createOrder(LocalDate date, LocalTime time, Integer totalAmount,
                                  String status, User user) {
    Order order = new Order();
    order.orderDate = date;
    order.orderTime = time;
    order.totalOrderAmount = totalAmount;
    order.orderStatus = status;
    order.user = user;
    return order;
  }

  @Override
  public String toString() {
    return "Order [orderId=" + orderId + ", orderDate=" + orderDate + ", orderTime=" + orderTime + ", totalOrderAmount="
        + totalOrderAmount + ", orderStatus=" + orderStatus + ", user=" + user + "]";
  }
  
}
