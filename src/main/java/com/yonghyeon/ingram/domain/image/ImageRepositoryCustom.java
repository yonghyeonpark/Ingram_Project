package com.yonghyeon.ingram.domain.image;

import com.yonghyeon.ingram.web.dto.image.ImageSearch;

import java.util.List;

public interface ImageRepositoryCustom {

    List<Image> mgetImages(Long principalId, ImageSearch imageSearch);

}
