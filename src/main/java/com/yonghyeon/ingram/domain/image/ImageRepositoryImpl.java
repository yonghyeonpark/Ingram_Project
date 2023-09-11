package com.yonghyeon.ingram.domain.image;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yonghyeon.ingram.domain.bookmark.QBookmark;
import com.yonghyeon.ingram.domain.follow.QFollow;
import com.yonghyeon.ingram.domain.likes.QLikes;
import com.yonghyeon.ingram.domain.user.QUser;
import com.yonghyeon.ingram.web.dto.image.ImageSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.*;
import static com.yonghyeon.ingram.domain.follow.QFollow.*;
import static com.yonghyeon.ingram.domain.image.QImage.*;
import static com.yonghyeon.ingram.domain.likes.QLikes.*;

@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Image> mGetImages(Long principalId, ImageSearch imageSearch) {

        return jpaQueryFactory
                .selectFrom(image)
                .where(image.user.id.in(select(follow.toUser.id)
                        .from(follow)
                        .where(follow.fromUser.id.eq(principalId))))
                .orderBy(image.id.desc())
                .limit(imageSearch.getSize())
                .offset(imageSearch.getOffset())
                .fetch();
    }
}
