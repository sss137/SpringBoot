package org.shark.boot04.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

import org.shark.boot04.dto.BoardDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

  @GetMapping("/")
  public String index() {
    return "index";   //templates/index.html을 의미합니다.
  }
 
  @GetMapping("/value/expr")
  public String value(Model model, HttpSession session) {
    model.addAttribute("name", "jackson");
    model.addAttribute("nickname", "<h3>Hulk</h3>");
    model.addAttribute("board", new BoardDTO(1L, "제목", "내용", LocalDateTime.now()));
    
    session.setAttribute("forecast", Map.of("weather", "rainy", "temperature", 27));
    return "value";
  }
  
  @GetMapping("/utility")
  public String utility(Model model) {
    model.addAttribute("title", "Spring Boot");
    model.addAttribute("content", "");
    model.addAttribute("hit", 1234);
    model.addAttribute("authors", Arrays.asList("김철수", "이영희"));
    model.addAttribute("pubdate", LocalDateTime.now());
    return "utility";
  }
  
  @GetMapping("/message/expr")
  public String message() {
    return "message";
  }
  
}



