package com.codingrecipe.board.exception;

public class BoardNotFoundException extends RuntimeException { //uncheckedException

    private static final String MESSAGE = "존재하지 않는 게시글입니다.";

    public BoardNotFoundException() {
        super(MESSAGE);
    }

    public BoardNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }

}
