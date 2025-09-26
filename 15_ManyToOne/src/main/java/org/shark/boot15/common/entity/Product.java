package org.shark.boot15.common.entity;

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

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id")
  private Long id;
  
  @Column(name = "product_name")
  private String productName;
  
  private Double price;
  private Integer stock;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;
  
  protected Product() {}
  
  public static Product createProducts(String productName, Double price, Integer stock, Category category) {
    Product product = new Product();
    product.productName = productName;
    product.price = price;
    product.stock = stock;
    product.category = category;
    return product;
  }

  public void changeCategory(Category category) {
    this.category = category;
  }

  public void updatePrice(Double price) {
      this.price = price;
  }
  
  @Override
  public String toString() {
    return "Product [id=" + id + ", productName=" + productName + ", price=" + price + ", stock=" + stock
        + ", category=" + category + "]";
  }

}


