package com.yonghyeon.ingram.web.dto.image;

import lombok.Data;

@Data
public class ImageSearch {

    private static final int Max_SIZE = 2000;

    private Integer page;
    private Integer size;

    public long getOffset() {
        // 음수값 나오는걸 방지
        return (long) (Math.max(1, page)-1) * Math.min(size, Max_SIZE);
    }
}