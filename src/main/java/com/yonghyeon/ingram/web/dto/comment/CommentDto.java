package com.yonghyeon.ingram.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CommentDto {

    @NotNull
    private Long imageId;

    @NotBlank
    private String content;

    // toEntity 필요없음.
}
