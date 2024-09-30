package com.codingrecipe.board.service;

import com.codingrecipe.board.dto.BoardDTO;
import com.codingrecipe.board.dto.BoardFileDTO;
import com.codingrecipe.board.exception.BoardNotFoundException;
import com.codingrecipe.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service //구현체 클래스에 붙임 (지금은 인터페이스/구현체 안 나눠져 있음)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

/*
    게시글 등록
    1) 파일 첨부 / 2) 다중 파일 첨부
*/
    //1) 파일 첨부
//    @Transactional
//    public void save(BoardDTO boardDTO) throws IOException {
//
//        //파일 첨부 여부 확인
//        if (boardDTO.getBoardFile().isEmpty()) {
//            // 파일 없는 경우
//            boardDTO.setFileAttached(0); //파일 첨부 여부 필드에 0 저장
//            boardRepository.save(boardDTO);
//        } else {
//            // 파일 있는 경우
//            boardDTO.setFileAttached(1); //파일 첨부 여부 필드에 1 저장
//            // 어떤 게시글에 대한 첨부파일인지 알아야 됨 => 게시글 저장 후 id값 활용을 위해 리턴 받음.
//            BoardDTO savedBoard = boardRepository.save(boardDTO);
//            // 파일만 따로 가져오기 (게시글 작성 시 첨부한 파일 담는 필드)
//            MultipartFile boardFile = boardDTO.getBoardFile();
//            // 파일 이름 가져오기 (원본 파일명)
//            String originalFilename = boardFile.getOriginalFilename();
//            log.info("originalFilename= {}", originalFilename);
//
//            // 저장용 이름 만들기
//            log.info("Current time in milliseconds: {}", System.currentTimeMillis());
//            String storedFileName = System.currentTimeMillis() + "-" + originalFilename;
//            log.info("storedFileName= {}" + storedFileName);
//
//            // BoardFileDTO 세팅 ( DB에는 저장용 파일명만 저장함! )
//            BoardFileDTO boardFileDTO = new BoardFileDTO();
//            boardFileDTO.setOriginalFileName(originalFilename);
//            boardFileDTO.setStoredFileName(storedFileName);
//            boardFileDTO.setBoardId(savedBoard.getId());
//
//            // 파일 저장용 폴더에 파일 저장 처리
//            String savePath = "C:/development/intellij_community/spring_upload_files/" + storedFileName; //window
//            //String savePath = "/Users/codingrecipe/development/intellij_community/spring_upload_files/" + storedFileName; // mac
//            boardFile.transferTo(new File(savePath));
//            // board_file_table 저장 처리
//            boardRepository.saveFile(boardFileDTO);
//        }
//    }

    //2) 다중 파일 첨부
    @Transactional
    public void save(BoardDTO boardDTO) throws IOException {
        //파일 여부 확인
        if (boardDTO.getBoardFile().get(0).isEmpty()) {
            boardDTO.setFileAttached(0);
            boardRepository.save(boardDTO);
        } else {
            boardDTO.setFileAttached(1);
            BoardDTO savedBoard = boardRepository.save(boardDTO);

            //다중 파일이므로
            for (MultipartFile boardFile : boardDTO.getBoardFile()) {
                String originalFilename = boardFile.getOriginalFilename();
                log.info("originalFilename= {}", originalFilename);
    
                // 저장용 이름 만들기
                log.info("Current time in milliseconds: {}", System.currentTimeMillis());
                String storedFileName = System.currentTimeMillis() + "-" + originalFilename;
                log.info("storedFileName= {}", storedFileName);
    
                // BoardFileDTO 세팅 ( DB에는 저장용 파일명만 저장함! )
                BoardFileDTO boardFileDTO = new BoardFileDTO();
                boardFileDTO.setOriginalFileName(originalFilename);
                boardFileDTO.setStoredFileName(storedFileName);
                boardFileDTO.setBoardId(savedBoard.getId());
    
                // 파일 저장용 폴더에 파일 저장 처리
                String savePath = "C:/development/intellij_community/spring_upload_files/" + storedFileName; //window
                boardFile.transferTo(new File(savePath));
    
                boardRepository.saveFile(boardFileDTO);
            }
        }
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

//  1) 파일 조회
//    public BoardFileDTO findFile(Long id) {
//        return boardRepository.findFile(id);
//    }

//  2) 다중 파일 조회
    public List<BoardFileDTO> findFile(Long id) {
        return boardRepository.findFile(id);
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
