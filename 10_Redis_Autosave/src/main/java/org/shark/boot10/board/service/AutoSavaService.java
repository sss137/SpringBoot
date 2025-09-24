package org.shark.boot10.board.service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.shark.boot10.board.dto.BoardDTO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AutoSavaService {

  private final RedisTemplate<String, BoardDTO> redisTemplate;
  
  private static final String PREFIX = "form:autosave:";
  
  //임시 저장 : TTL 30분 설정
  public void saveDraft(Long uid, BoardDTO boardDTO) {
    boardDTO.setCreatedAt(LocalDateTime.now().toString());
    String key = PREFIX + uid;
    redisTemplate.opsForValue().set(key, boardDTO, 30, TimeUnit.MINUTES);
  }
  
  //임시 저장 데이터 불러오기
  public BoardDTO loadDraft(Long uid) {
    String key = PREFIX + uid;
    return redisTemplate.opsForValue().get(key);
  }
  
  //임시 저장 데이터 삭제
  public void deleteDraft(Long uid) {
    String key = PREFIX + uid;
    redisTemplate.delete(key);
  }
  
}


