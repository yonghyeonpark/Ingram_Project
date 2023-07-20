package com.yonghyeon.ingram.handler;

// html 파일로 return한 controller에 적용
public class CustomException extends RuntimeException{

    // 객체 구분할 때
    private static final long serialVersionUID = 1L;

    public CustomException(String message) {
        super(message);
    }
}
