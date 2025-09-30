package org.shark.boot17.product.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.shark.boot17.product.entity.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {

  private Integer categoryId;
  private String categoryName;
  private Integer parentCategoryId;
  private String parentCategoryName;
  private List<CategoryDTO> children;   //하위 카테고리 정보
  private List<ProductDTO> products;   //카테고리 소속 제품 정보
  
  public CategoryDTO() {}
  
  public CategoryDTO(Integer categoryId, String categoryName, Integer parentCategoryId, String parentCategoryName) {
    super();
    this.categoryId = categoryId;
    this.categoryName = categoryName;
    this.parentCategoryId = parentCategoryId;
    this.parentCategoryName = parentCategoryName;
  }
  
  //DTO -> Entity (상위 카테고리가 있는 경우)
  public Category toEntity(Category parent) {
    Category category = new Category();
    category.setCategoryId(categoryId);
    category.setCategoryName(categoryName);
    category.setParent(parent);
    return category;
  }
  
  //DTO -> Entity (상위 카테고리가 없는 경우)
  public Category toEntity() {
    return toEntity(null);
  }
  
  //Entity -> DTO
  public static CategoryDTO toDTO(Category entity) {
    CategoryDTO dto = new CategoryDTO();
    dto.setCategoryId(entity.getCategoryId());
    dto.setCategoryName(entity.getCategoryName());
    if (entity.getParent() != null) {
      dto.setParentCategoryId(entity.getParent().getCategoryId());
      dto.setParentCategoryName(entity.getParent().getCategoryName());
    }
    return dto;
  }
  
  //Entity -> DTO (하위 카테고리 포함)
  public static CategoryDTO toDTOWithChildren(Category entity) {
    CategoryDTO dto = toDTO(entity);
    if (entity.getChildren() != null && !entity.getChildren().isEmpty()) {
      List<CategoryDTO> children = entity.getChildren().stream()
                                         .map(category -> toDTO(category))
                                         .collect(Collectors.toList());
      dto.setChildren(children);
    }
    return dto;
  }
  
  //Entity -> DTO (제품 목록 포함)
  public static CategoryDTO toDTOWithProducts(Category entity) {
    CategoryDTO dto = toDTO(entity);
    if (entity.getProducts() != null && !entity.getProducts().isEmpty()) {
      List<ProductDTO> products = entity.getProducts().stream()
                                         .map(ProductDTO::toDTO)
                                         .collect(Collectors.toList());
      dto.setProducts(products);
    }
    return dto;
  }
  
  @Override
  public String toString() {
    return "CategoryDTO [categoryId=" + categoryId + ", categoryName=" + categoryName + ", parentCategoryId="
        + parentCategoryId + ", parentCategoryName=" + parentCategoryName + "]";
  }
  
}


