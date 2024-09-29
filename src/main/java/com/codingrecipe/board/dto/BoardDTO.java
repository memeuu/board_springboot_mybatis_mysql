package com.codingrecipe.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDTO {

    private Long id;
    private String boardTitle;
    private String boardWriter;
    private String boardPass; //password
    private String boardContents;
    private int boardHits;
    private String createdAt;

}
