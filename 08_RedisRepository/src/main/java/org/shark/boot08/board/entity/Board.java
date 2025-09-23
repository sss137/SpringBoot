package org.shark.boot08.board.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

@RedisHash(value = "board",   //board를 key의 prefix로 사용(board:1, board:2, ...)합니다.
           timeToLive = 30)   //이 데이터는 Redis에 30초 동안만 유지됩니다. 30초 이후 자동으로 삭제됩니다.
public class Board {

  //Redis에 Board 엔티티 저장 시 board:{bid} 형식의 key를 사용합니다.
  @Id
  private Long bid;
  
  private String title;
  private String content;
  private LocalDateTime createdAt;
  
}


