package com.yonghyeon.ingram.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    @GetMapping("/json")
    public String json() {
        return "{\"user\":\"pyh\"}";
    }

    // MessageConverter가 자동으로 JavaObject를 Json으로 변경해서 통신을 통해 응답을 해준다.
    // @RestController 일때만 MessageConverter가 작동
}
