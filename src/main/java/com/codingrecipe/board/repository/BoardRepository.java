package com.codingrecipe.board.repository;

import com.codingrecipe.board.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor //mybatis 주입
public class BoardRepository {

    //MyBatis 프레임워크에서 제공하는 클래스로 SQL 세션을 간편하게 관리 (의존관계 주입받아 사용)
    private final SqlSessionTemplate sql; //mapper.xml

/*
    게시글 등록
*/
    public void save(BoardDTO boardDTO) {
        //insert(statement, parameter) - 파라미터 한 개만 넘길 수 있음
        // => 여러 개 넘기려면 HashMap 타입으로 넘길 수 있음 (mapper.xml에 parameterType="map")
        sql.insert("Board.save", boardDTO); //Board = mapper의 namespace 속성
    }


/*
    게시판 목록 
*/
    public List<BoardDTO> findAll() {
        return sql.selectList("Board.findAll");
    }

/*
    게시글 단건조회
*/
    public Optional<BoardDTO> findById(Long id) {
        BoardDTO board = sql.selectOne("Board.findById", id);
        return Optional.ofNullable(board); //null이면 Optional.empty()반환
    }

    public void updateHits(Long id) {
        sql.update("Board.updateHits", id);
    }

    public void update(BoardDTO board) {
        sql.update("Board.update", board);
    }

/*
    게시글 삭제
*/
    public void delete(Long id) {
        sql.delete("Board.delete", id);
    }
}
