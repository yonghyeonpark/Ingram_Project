package com.yonghyeon.ingram.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CMResponseDto<T> {

    private Long code; // 성공(1), 실패(0)
    private String message;
    private T data;

    public CMResponseDto(Long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
