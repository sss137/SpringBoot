package org.shark.boot16.product.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")

@Getter
@Setter
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id")
  private Integer productId;

  @Column(name = "product_name", nullable = false, length = 100)
  private String productName;

  @Column(name = "product_price", nullable = false)
  private Integer productPrice;

  @Column(name = "stock_quantity", nullable = false)
  private Integer stockQuantity;

  @Column(name = "sale_status_yn", nullable = false)
  private Boolean saleStatusYn = true;

  @Column(name = "product_description", columnDefinition = "TEXT")
  private String productDescription;

  @Column(name = "register_date", nullable = false)
  private LocalDateTime registerDate;

  // 하나의 카테고리에는 여러 개의 제품이 포함됩니다. (1:M)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

  protected Product() {}

  public static Product createProduct(String productName, Integer productPrice, Integer stockQuantity,
                                      Boolean saleStatusYn, String productDescription, LocalDateTime registerDate,
                                      Category category) {
    Product product = new Product();
    product.setProductName(productName);
    product.setProductPrice(productPrice);
    product.setStockQuantity(stockQuantity);
    product.setSaleStatusYn(saleStatusYn);
    product.setProductDescription(productDescription);
    product.setRegisterDate(registerDate);
    product.setCategory(category);
    return product;
  }

  @Override
  public String toString() {
    return "Product [productId=" + productId + ", productName=" + productName + ", productPrice=" + productPrice
        + ", stockQuantity=" + stockQuantity + ", saleStatusYn=" + saleStatusYn + ", productDescription="
        + productDescription + ", registerDate=" + registerDate + ", category=" + category + "]";
  }
  
}
