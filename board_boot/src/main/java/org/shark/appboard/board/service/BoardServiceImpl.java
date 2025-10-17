package org.shark.appboard.board.service;

import org.shark.appboard.board.dto.BoardDTO;
import org.shark.appboard.board.entity.Board;
import org.shark.appboard.board.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoardServiceImpl implements BoardService {

  private final BoardRepository boardRepository;
  
  @Transactional(readOnly = true)
  @Override
  public Page<BoardDTO> getAllBoards(Pageable pageable) {
    // 페이징 처리: 클라이언트는 page=1, JpaRepository는 page=0
    pageable = pageable.withPage(pageable.getPageNumber() - 1);
    log.info("페이지: {}, 크기: {}", pageable.getPageNumber(), pageable.getPageSize());
    Page<Board> boardPage = boardRepository.findAll(pageable);
    return boardPage.map(BoardDTO::toDTO);
  }

  @Transactional(readOnly = true)
  @Override
  public BoardDTO getBoardById(Long bid) {
    log.info("게시글조회 - bid: {}", bid);
    Board board = boardRepository.findById(bid)
        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. bid: " + bid));
    return BoardDTO.toDTO(board);
  }

  @Override
  public BoardDTO createBoard(BoardDTO boardDTO) {
    log.info("게시글 생성 - 제목: {}", boardDTO.getTitle());
    Board board = boardDTO.toEntity();
    Board saved = boardRepository.save(board);
    log.info("게시글 생성 완료: {}", saved);
    return BoardDTO.toDTO(saved);
  }

  @Override
  public BoardDTO updateBoard(Long bid, BoardDTO boardDTO) {
    log.info("게시글 수정 - bid: {}, 제목: {}", bid, boardDTO.getTitle());
    Board foundBoard = boardRepository.findById(bid)
        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. bid: " + bid));
    foundBoard.updateBoard(boardDTO.getTitle(), boardDTO.getContent());
    return BoardDTO.toDTO(foundBoard);
  }

  @Override
  public void deleteBoard(Long bid) {
    log.info("게시글 삭제 - bid: {}", bid);
    Board foundBoard = boardRepository.findById(bid)
        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. bid: " + bid));
    boardRepository.delete(foundBoard);
    log.info("게시글 삭제 완료 - bid: {}", bid);
  }

}