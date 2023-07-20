package com.yonghyeon.ingram.web.controller;

import com.yonghyeon.ingram.domain.user.User;
import com.yonghyeon.ingram.handler.CustomValidationException;
import com.yonghyeon.ingram.service.AuthService;
import com.yonghyeon.ingram.web.dto.auth.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;

    @GetMapping("/auth/signin")
    public String signinForm() {
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm() {
        return "auth/signup";
    }

    @PostMapping("/auth/signup")
    // 에러가 발생하면 bindingResult(getFieldErrors)에 담음
    public String signup(@Valid SignupRequestDto requestDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            Map<String,String> errorMap = new HashMap<>();

            for(FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new CustomValidationException("유효성 검사 에러", errorMap);
        } else {
            authService.join(requestDto);
            return "auth/signin";
        }
    }
}
