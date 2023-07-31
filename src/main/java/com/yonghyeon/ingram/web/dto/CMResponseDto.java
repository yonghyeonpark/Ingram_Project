package com.yonghyeon.ingram.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CMResponseDto<T> {

    private Long code; // 성공(1), 실패(0)
    private String message;
    private T data;

}
