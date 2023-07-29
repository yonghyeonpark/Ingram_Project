package com.yonghyeon.ingram.web.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
public class UserPasswordUpdateDto {

    @NotBlank
    private String newPassword;

    private String newPasswordCheck;
}
