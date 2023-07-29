package com.yonghyeon.ingram.web.controller;

import com.yonghyeon.ingram.config.auth.PrincipalDetails;
import com.yonghyeon.ingram.service.UserService;
import com.yonghyeon.ingram.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;


    @GetMapping("/user/{pageUserId}")
    public String profile(@PathVariable Long pageUserId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        UserProfileDto dto = userService.userProfile(pageUserId, principalDetails.getUser().getId());

        model.addAttribute("dto", dto);
        return "user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String update(@PathVariable Long id/*, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model*/) {

        //model.addAttribute("principal", principalDetails.getUser());

        return "user/update";
    }

    @GetMapping("/user/{id}/passwordUpdate")
    public String passwordUpdate(@PathVariable Long id) {

        return "user/passwordUpdate";
    }

}
