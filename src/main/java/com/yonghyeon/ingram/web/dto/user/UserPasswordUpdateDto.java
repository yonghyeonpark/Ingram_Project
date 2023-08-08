package com.yonghyeon.ingram.web.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
public class UserPasswordUpdateDto {

    @NotBlank
    private String nowPasswordCheck;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String newPasswordCheck;

    @Builder
    public UserPasswordUpdateDto(String nowPasswordCheck, String newPassword, String newPasswordCheck) {
        this.nowPasswordCheck = nowPasswordCheck;
        this.newPassword = newPassword;
        this.newPasswordCheck = newPasswordCheck;
    }
}
