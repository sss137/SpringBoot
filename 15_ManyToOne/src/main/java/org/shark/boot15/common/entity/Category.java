package org.shark.boot15.common.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id")
  private Long id;
  
  @Column(name = "category_name")
  private String categoryName;
  
  private String description;
  
  //양방향 매핑 추가
  @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
  private List<Product> products = new ArrayList<>();
  
  protected Category() {}
  
  public static Category createCategories(String categoryName, String description) {
    Category category  = new Category();
    category .categoryName = categoryName;
    category .description = description;
    return category;
  }
  
  public void addProduct(Product product) {
    products.add(product);
    product.setCategory(this);
  }
  
  public void removeProduct(Product product) {
    products.remove(product);
    product.setCategory(null);
  }

  @Override
  public String toString() {
    return "Category [id=" + id + ", categoryName=" + categoryName + ", description=" + description + ", productCount="
        + products.size() + "]";
  }

  
}


