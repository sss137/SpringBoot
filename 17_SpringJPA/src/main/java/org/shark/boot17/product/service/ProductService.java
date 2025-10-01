package org.shark.boot17.product.service;

import org.shark.boot17.product.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
  ProductDTO saveProduct(ProductDTO dto);
  ProductDTO updateProduct(ProductDTO dto);
  void deleteProduct(Integer productId);
  ProductDTO findProductById(Integer productId);
  Page<ProductDTO> findProductList(Pageable pageable);
  Page<ProductDTO> findProductListByCategory(Integer categoryId, Pageable pageable);
  
}


