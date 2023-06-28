package com.yonghyeon.ingram.web.controller;

import com.yonghyeon.ingram.domain.user.User;
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
    // @ResponseBody 사용으로 @Controller이지만 데이터를 응답함
    // 오류가 발생하면 bindingResult(getFieldErrors)에 담음
    public @ResponseBody String signup(@Valid SignupRequestDto requestDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            Map<String,String> errorMap = new HashMap<>();

            for(FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return "오류 페이지";
        } else {
            User userEntity = authService.join(requestDto);
            return "auth/signin";
        }
    }
}
