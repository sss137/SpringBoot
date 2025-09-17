package org.shark.boot05.board.controller;

import java.util.Map;

import org.shark.boot05.board.dto.BoardDTO;
import org.shark.boot05.board.service.BoardService;
import org.shark.boot05.common.dto.PageDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

/*
 * 홈화면   GET   /
 * 목록     GET   /board/list?page=1&size=20&sort=DESC
 * 등록폼   GET   /board/write
 * 등록     POST  /board/create
 * 상세     GET   /board/detail?bid=1
 * 수정폼   GET   /board/edit?bid=1
 * 수정     POST  /board/update
 * 삭제     POST  /board/delete
 */
@RequiredArgsConstructor
@Controller
public class BoardController {

  private final BoardService boardService;
  
  @GetMapping("/")
  public String home() {
    return "home";   //SpringResourceTemplateResolver에 의해서 prefix, suffix가 자동으로 추가
                      //prefix="/templates/", suffix=".html"
  }
  
  @GetMapping("/board/list")
  public String list(PageDTO dto,
                      @RequestParam(value = "sort", defaultValue = "DESC") String sort,
                      Model model) {
    if (sort.isEmpty() || (!sort.equalsIgnoreCase("asc") && !sort.equalsIgnoreCase("desc"))) {
      sort = "DESC";
    }
    Map<String, Object> result = boardService.getBoardList(dto, sort);
    model.addAttribute("boardList", result.get("boardList"));
    model.addAttribute("pageDTO", result.get("pageDTO"));
    return "board/list";
  }
  
  @GetMapping("/detail")
  public String detail(@RequestParam(value = "bid") Long bid, Model model) {
    model.addAttribute("board", boardService.getBoardById(bid));
    return "board/detail";
  }
  
  @PostMapping("/write")
  public String write(BoardDTO board, RedirectAttributes redirectAttr) {
    redirectAttr.addFlashAttribute("msg", boardService.createBoard(board) ? "등록 성공" : "등록 실패");
    return "redirect:/board/list";
  }
  
  @PostMapping("/update")
  public String update(BoardDTO board, RedirectAttributes redirectAttr) {
    //성공하면 상세, 실패하면 목록
    Boolean success = boardService.updateBoard(board);
    if (success) {
      redirectAttr.addFlashAttribute("msg", "수정 성공")
                  .addAttribute("bid", board.getBid());
      return "redirect:/board/detail";
    } else {
      redirectAttr.addFlashAttribute("msg", "수정 실패");
      return "redirect:/board/list";
    }
    
  }
  
  @PostMapping("/delete")
  public String delete(Long bid, RedirectAttributes redirectAttr) {
    redirectAttr.addFlashAttribute("msg", boardService.deleteBoard(bid) ? "삭제 성공" : "삭제 실패");
    return "redirect:/board/list";
  }
  
}


