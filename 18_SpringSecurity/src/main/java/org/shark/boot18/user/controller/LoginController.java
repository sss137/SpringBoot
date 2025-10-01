package org.shark.boot18.user.controller;

import org.shark.boot18.user.dto.LoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

  @GetMapping
  public String login(@ModelAttribute LoginDTO dto) {   //파라미터에 객체를 넣으면 @ModelAttribute가 없어도 알아서 적용됨
                                                         //Model에 저장한 이름은 loginDTO입니다.
    return "login";   //templates/login.html
  }
  
}


