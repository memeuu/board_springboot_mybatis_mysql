package com.codingrecipe.board.controller;

import com.codingrecipe.board.dto.BoardDTO;
import com.codingrecipe.board.exception.BoardNotFoundException;
import com.codingrecipe.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor //final 필드와 @NotNull로 선언된 필드에 대해 생성자 자동으로 생성해 줌
public class BoardController {

    private final BoardService boardService;

//    @Autowired
//    public BoardController(BoardService boardService) {
//        this.boardService = boardService;
//    }


/*
    게시글 등록
*/
    @GetMapping("/save")
    public String save() {
        return "save";
    }
    
    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) {
        //log.info("boardDTO= {}", boardDTO);
        boardService.save(boardDTO);
        //return "redirect:/index.html"; //static 폴더에 있는 HTML 파일 직접 접근 (확장자 빼고 index 해도 됨)
        return "redirect:/list"; //templates 파일의 경우
    }

/*
    게시판 목록
*/
    @GetMapping("/list")
    public String findAll(Model model) {
        List<BoardDTO> boardDTOList = boardService.findAll();
        //log.info("boardDTOList= {}", boardDTOList);
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }

/*
    게시글 단건조회
*/
    @GetMapping("/board/{id}")
    public String findById(@PathVariable Long id, Model model) {
        //상세내용 조회
        BoardDTO board = boardService.findById(id);
        //log.info("board= {}", board);
        model.addAttribute("board", board);
        return "detail";
    }

    //특정 예외(사용자 정의)
    @ExceptionHandler(BoardNotFoundException.class)
    public String boardNotFoundEx(BoardNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

/*
    게시글 수정: 제목, 내용
*/
    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        BoardDTO board = boardService.findById(id);
        model.addAttribute("board", board);
        return "update";
    }

    //이 방식이 RESTful API 디자인에 부합: URL에서 수정할 리소스(ID)를 명확히 명시
    //+ PRG 패턴: POST-Redirect-Get (redirect 처리로 페이지 새로고침 시 POST요청 반복 X)
    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute BoardDTO board) {
        board.setId(id); //명시적인 데이터 전달
        boardService.update(board);
        return "redirect:/board/{id}";
    }
    //※ 근데 이렇게 구현할 시 수정 후 상세 페이지로 돌아가 조회수가 증가하는 것 막기 위해
    //   쿠키 사용 등? 다른 추가 처리있어야 된다.


//    @PostMapping("/update/{id}")
//    public String update(BoardDTO boardDTO, Model model) {
//        boardService.update(boardDTO);
//        BoardDTO board = boardService.findById(boardDTO.getId());
//        model.addAttribute("board", board);
//        return "detail";
//    }
    
/*
    게시글 삭제
*/
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.delete(id);
        return "redirect:/list";
    }


}
