package com.codingrecipe.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    private int fileAttached; //해당 게시글의 파일 첨부 여부

    //게시글 작성 시 첨부파일 담는 필드
    //MultipartFile boardFile; //파일 1개
    private List<MultipartFile> boardFile; //다중파일
}
