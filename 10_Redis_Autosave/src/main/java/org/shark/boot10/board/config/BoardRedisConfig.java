package org.shark.boot10.board.config;

import org.shark.boot10.board.dto.BoardDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class BoardRedisConfig {

  @Value("${spring.data.redis.host}")  
  private String host;
  
  @Value("${spring.data.redis.port}")  
  private int port;
  
  @Bean
  RedisConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory(host, port);
  }
  
  @Bean
  RedisTemplate<String, BoardDTO> redisTemplateBoard() {
    RedisTemplate<String, BoardDTO> redisTemplate = new RedisTemplate<>();
    //Redis 연결을 위한 Factory 등록
    redisTemplate.setConnectionFactory(redisConnectionFactory());
    //Redis 통신을 위한 직렬화
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());   //Jackson 라이브러리를 기반으로하는 직렬화기 사용
    return redisTemplate;
  }
  
}


