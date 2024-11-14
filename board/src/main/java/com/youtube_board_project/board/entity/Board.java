package com.youtube_board_project.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity  //이 클래스는 db에 있는 테이블을 의미한다. 이클래스는 db의 테이블과 일치해야 된다.
@Data  //롬복
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String content;

    private String filename;

    private String filepath;

}
