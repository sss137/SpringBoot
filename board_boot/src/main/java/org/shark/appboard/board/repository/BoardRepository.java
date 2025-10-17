package org.shark.appboard.board.repository;

import org.shark.appboard.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>{
  
  

}


