package com.youtube_board_project.board.repository;

import com.youtube_board_project.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.beans.JavaBean;

@Repository                                       //어떤 엔티티,엔티티의 id의 타입
public interface BoardRepository extends JpaRepository<Board,Integer> {
}
