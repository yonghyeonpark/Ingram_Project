package com.yonghyeon.ingram.handler;

public class CustomException extends RuntimeException{

    // 객체 구분할 때
    private static final long serialVersionUID = 1L;

    public CustomException(String message) {
        super(message);
    }
}
