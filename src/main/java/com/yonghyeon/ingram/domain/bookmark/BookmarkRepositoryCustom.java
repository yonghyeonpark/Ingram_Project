package com.yonghyeon.ingram.domain.bookmark;

import com.yonghyeon.ingram.domain.image.Image;
import com.yonghyeon.ingram.web.dto.image.ImageSearch;

import java.util.List;

public interface BookmarkRepositoryCustom {
    void mBookmark(Long principalId, Long imageId);

    void mUnBookmark(Long principalId, Long imageId);

    List<Image> mBookmarkImages(Long principalId);
}
