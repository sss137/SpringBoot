package org.shark.boot17.product.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id")
  private Integer categoryId;

  @Column(name = "category_name", nullable = false, length = 50)
  private String categoryName;

  // 하위 카테고리(category_id)와 상위 카테고리(parent_category_id)는 M:1 관계입니다.
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_category_id")
  private Category parent;

  @OneToMany(mappedBy = "parent")  // Self Join 양뱡향 설정
  private List<Category> children;

  @OneToMany(mappedBy = "category")  // Product 양방향 설정
  private List<Product> products;

  public Category() {}

  public static Category createCategory(String categoryName, Category parent) {
    Category category = new Category();
    category.setCategoryName(categoryName);
    category.setParent(parent);
    return category;
  }

  // @ManyToOne은 포함, @OneToMany는 불포함
  @Override
  public String toString() {
    return "Category [categoryId=" + categoryId 
         + ", categoryName=" + categoryName 
         + ", parent=" + (parent != null ? parent : null) + "]";
  }
  
}
