package com.yonghyeon.ingram.web.controller;

import com.yonghyeon.ingram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{id}")
    public String profile(@PathVariable Long id, Model model) {
        model.addAttribute("images", null);
        return "user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String update(@PathVariable Long id/*, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model*/) {

        //model.addAttribute("principal", principalDetails.getUser());

        return "user/update";
    }

}
