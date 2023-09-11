package com.yonghyeon.ingram.domain.likes;

import com.yonghyeon.ingram.domain.image.Image;
import com.yonghyeon.ingram.web.dto.image.ImageSearch;

import java.util.List;

public interface LikesRepositoryCustom {

    void mLikes(Long principalId, Long imageId);

    void mUnLikes(Long principalId, Long imageId);
}
