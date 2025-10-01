package org.shark.boot17.product.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;

//----- 페이징 처리를 위한 응답 객체
//      어떤 DTO 목록을 처리하는지 제네릭 타입으로 처리합니다.
//      (ApiPageResponse<ProductDTO> 등)

@Getter
@Setter
public class ApiPageResponse<T> {

  private List<T> content;
  private String message;
  private PageInfo pageInfo;
  
  public ApiPageResponse(Page<T> page) {
    this.content = page.getContent();
    this.message = "목록 조회 성공";
    this.pageInfo = new PageInfo(page);
  }
  
  @Getter
  @Setter
  public static class PageInfo {
    private int currentPage;  // 현재 페이지 (0부터 시작)
    private int size;         // 페이지 개수
    private long totalItem;   // 전체 요소 개수
    private int totalPage;    // 전체 페이지 수
    private boolean isFirst;  // 첫 페이지 여부
    private boolean isLast;   // 마지막 페이지 여부
    
    public PageInfo(Page<?> page) {
      this.currentPage = page.getNumber();
      this.size = page.getSize();
      this.totalItem = page.getTotalElements();
      this.totalPage = page.getTotalPages();
      this.isFirst = page.isFirst();
      this.isLast = page.isLast();
    }
  }
  
}


