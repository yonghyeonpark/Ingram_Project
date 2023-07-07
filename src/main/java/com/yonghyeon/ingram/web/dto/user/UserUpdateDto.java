package com.yonghyeon.ingram.web.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
public class UserUpdateDto {

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    private String website;
    private String bio;
    private String phonenum;
    private String gender;

    @Builder
    public UserUpdateDto(String name, String password, String website, String bio, String phonenum, String gender) {
        this.name = name;
        this.password = password;
        this.website = website;
        this.bio = bio;
        this.phonenum = phonenum;
        this.gender = gender;
    }
}
