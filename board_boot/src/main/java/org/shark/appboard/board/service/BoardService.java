package org.shark.appboard.board.service;

import org.shark.appboard.board.dto.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {
  Page<BoardDTO> getAllBoards(Pageable pageable);
  BoardDTO getBoardById(Long bid);
  BoardDTO createBoard(BoardDTO boardDTO);
  BoardDTO updateBoard(Long bid, BoardDTO boardDTO);
  void deleteBoard(Long bid);
}