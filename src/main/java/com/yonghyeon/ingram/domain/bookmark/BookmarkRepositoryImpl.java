package com.yonghyeon.ingram.domain.bookmark;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yonghyeon.ingram.domain.follow.QFollow;
import com.yonghyeon.ingram.domain.image.Image;
import com.yonghyeon.ingram.domain.image.QImage;
import com.yonghyeon.ingram.web.dto.image.ImageSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BookmarkRepositoryImpl implements BookmarkRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Image> mBookmarkImages(Long principalId) {

        QBookmark qBookmark = QBookmark.bookmark;

        return jpaQueryFactory
                .select(qBookmark.image)
                .from(qBookmark)
                .where(qBookmark.user.id.eq(principalId))
                .fetch();
    }
}
