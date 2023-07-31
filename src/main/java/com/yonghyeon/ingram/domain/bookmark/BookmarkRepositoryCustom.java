package com.yonghyeon.ingram.domain.bookmark;

import com.yonghyeon.ingram.domain.image.Image;
import com.yonghyeon.ingram.web.dto.image.ImageSearch;

import java.util.List;

public interface BookmarkRepositoryCustom {

    List<Image> mBookmarkImages(Long principalId);
}
