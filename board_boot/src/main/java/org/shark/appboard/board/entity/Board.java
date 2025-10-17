package org.shark.appboard.board.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "boards")
@DynamicUpdate

@Getter

@EntityListeners(AuditingEntityListener.class)  // Spring Data JPA의 Auditing 기능 활성화
public class Board {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long bid;
  
  @Column(nullable = false, length = 200)
  private String title;
  
  @Column(columnDefinition = "TEXT")
  private String content;
  
  @CreatedDate
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;
  
  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  protected Board() { }
  
  private Board(String title, String content) {
    this.title = title;
    this.content = content;
  }
  
  public static Board createBoard(String title, String content) {
    return new Board(title, content);
  }
  
  public void updateBoard(String title, String content) {
    this.title = title;
    this.content = content;
  }
  
  @Override
  public String toString() {
  return "Board [bid=" + bid + ", title=" + title + ", content=" + content + ", createdAt=" + createdAt
      + ", updatedAt=" + updatedAt + "]";
  }
  
}