package com.yonghyeon.ingram.web.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
public class UserUpdateDto {

    @NotBlank
    private String name;

    private String website;
    private String bio;
    private String phonenum;
    private String gender;

}
