package com.codingrecipe.board.service;

import com.codingrecipe.board.dto.BoardDTO;
import com.codingrecipe.board.exception.BoardNotFoundException;
import com.codingrecipe.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service //구현체 클래스에 붙임 (지금은 인터페이스/구현체 안 나눠져 있음)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

/*
    게시글 등록
*/
    public void save(BoardDTO boardDTO) {
        boardRepository.save(boardDTO);
    }

/*
    게시판 목록 
*/
    public List<BoardDTO> findAll() {
        return boardRepository.findAll();
    }

/*
    게시글 단건조회
*/
    @Transactional
    public BoardDTO findById(Long id) {
        boardRepository.updateHits(id); //조회수 증가
        return boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException());
//              .orElseThrow(BoardNotFoundException::new); //람다식 대신 메서드 참조하여 더 간결하게 작성
    }

/*
    게시글 수정
*/
    public void update(BoardDTO board) {
        boardRepository.update(board);
    }

/*
    게시글 삭제
*/
    public void delete(Long id) {
        boardRepository.delete(id);
    }
}
