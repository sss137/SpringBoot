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
@Table(name = "payments")
public class Payment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "payment_id")
  private Integer paymentId;

  @Column(name = "payment_date", nullable = false)
  private LocalDate paymentDate;

  @Column(name = "payment_time", nullable = false)
  private LocalTime paymentTime;

  @Column(name = "payment_amount", nullable = false)
  private Integer paymentAmount;

  @Column(name = "payment_type", nullable = false, length = 20)
  private String paymentType;

  @Column(name = "payment_status", nullable = false, length = 20)
  private String paymentStatus = "COMPLETED";

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "payment")
  private List<OrderPayment> orderPayments;

  protected Payment() {}

  public static Payment createPayment(LocalDate date, LocalTime time, Integer amount,
                                      String type, String status, User user) {
    Payment payment = new Payment();
    payment.paymentDate = date;
    payment.paymentTime = time;
    payment.paymentAmount = amount;
    payment.paymentType = type;
    payment.paymentStatus = status;
    payment.user = user;
    return payment;
  }

  @Override
  public String toString() {
    return "Payment [paymentId=" + paymentId + ", paymentDate=" + paymentDate + ", paymentTime=" + paymentTime + ", paymentAmount="
        + paymentAmount + ", paymentType=" + paymentType + ", paymentStatus=" + paymentStatus + ", user=" + user + "]";
  }

}
