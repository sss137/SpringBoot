package org.shark.boot17.product.dto;

import java.time.LocalDateTime;

import org.shark.boot17.product.entity.Category;
import org.shark.boot17.product.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductDTO {

  private Integer productId;
  private String productName;
  private Integer productPrice;
  private Integer stockQuantity;
  private Boolean saleStatusYn;
  private String productDescription;
  private LocalDateTime registerDate;
  private Integer categoryId;
  private String categoryName;
  
  //DTO -> Entity
  public Product toEntity(Category category) {   //연관관계에 있는 Category 정보를 받아옵니다.
    Product product = new Product();
    product.setProductId(productId);
    product.setProductName(productName);
    product.setProductPrice(productPrice);
    product.setStockQuantity(stockQuantity);
    product.setSaleStatusYn(saleStatusYn);
    product.setProductDescription(productDescription);
    product.setRegisterDate(LocalDateTime.now());
    product.setCategory(category);
    return product;
  }
  
  //Entity -> DTO
  public static ProductDTO toDTO(Product entity) {
    ProductDTO dto = new ProductDTO();
    dto.setProductId(entity.getProductId());
    dto.setProductName(entity.getProductName());
    dto.setProductPrice(entity.getProductPrice());
    dto.setStockQuantity(entity.getStockQuantity());
    dto.setSaleStatusYn(entity.getSaleStatusYn());
    dto.setProductDescription(entity.getProductDescription());
    dto.setRegisterDate(entity.getRegisterDate());
    if (entity.getCategory() != null) {
      dto.setCategoryId(entity.getCategory().getCategoryId());
      dto.setCategoryName(entity.getCategory().getCategoryName());
    }
    return dto;
  }
  
}


