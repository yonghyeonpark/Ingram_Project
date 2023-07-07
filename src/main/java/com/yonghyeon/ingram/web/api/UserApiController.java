package com.yonghyeon.ingram.web.api;

import com.yonghyeon.ingram.config.auth.PrincipalDetails;
import com.yonghyeon.ingram.domain.user.User;
import com.yonghyeon.ingram.handler.CustomValidationApiException;
import com.yonghyeon.ingram.service.UserService;
import com.yonghyeon.ingram.web.dto.CMResponseDto;
import com.yonghyeon.ingram.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PutMapping("/api/user/{id}")
    public CMResponseDto<?> update(
            @PathVariable Long id,
            @Valid UserUpdateDto updateDto,
            BindingResult bindingResult, // 꼭 @Valid 다음 파라미터에 와야함
            @AuthenticationPrincipal PrincipalDetails principalDetails /*, Model model*/) {
        //model.addAttribute("principal", principalDetails.getUser());

        if(bindingResult.hasErrors()) {
            Map<String,String> errorMap = new HashMap<>();

            for(FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new CustomValidationApiException("유효성 검사 에러", errorMap);
        } else {
            User user = userService.userUpdate(id, updateDto);
            principalDetails.setUser(user);

            return new CMResponseDto<>(1L, "수정 완료", user);
        }
    }
}
