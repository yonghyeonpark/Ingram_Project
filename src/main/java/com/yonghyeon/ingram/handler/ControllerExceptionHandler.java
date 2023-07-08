package com.yonghyeon.ingram.handler;

import com.yonghyeon.ingram.util.Script;
import com.yonghyeon.ingram.web.dto.CMResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice // 모든 Exception을 낚아채옴
public class ControllerExceptionHandler {

    // javascript를 리턴
    @ExceptionHandler(CustomValidationException.class) // RuntimeException이 발동하는 모든 Exception을 감지
    public String validationException(CustomValidationException e) {
        // 1. 클라이언트에게 응답할 때는 Script
        // 2. Ajax 통신, Android 통신 - CMResponseDto
        if (e.getErrorMap() == null) {
            return Script.back(e.getMessage());
        } else {
            return Script.back(e.getErrorMap().toString());
        }
    }

    @ExceptionHandler(CustomException.class) // RuntimeException이 발동하는 모든 Exception을 감지
    public String Exception(CustomException e) {
        // 1. 클라이언트에게 응답할 때는 Script
        // 2. Ajax 통신, Android 통신 - CMResponseDto

        return Script.back(e.getMessage());
    }


    // data를 리턴(api로 통신할 때 / ajax)
    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationApiException(CustomValidationApiException e) {

        return new ResponseEntity<>(new CMResponseDto<>(0L, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e) {

        return new ResponseEntity<>(new CMResponseDto<>(0L, e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
}
