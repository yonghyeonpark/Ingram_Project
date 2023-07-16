package com.yonghyeon.ingram.domain.image;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yonghyeon.ingram.domain.follow.QFollow;
import com.yonghyeon.ingram.web.dto.image.ImageSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Image> mgetImages(Long principalId, ImageSearch imageSearch) {

        QImage qImage = QImage.image;
        QFollow qFollow = QFollow.follow;

        return jpaQueryFactory
                .selectFrom(qImage)
                .where(qImage.user.id.in(JPAExpressions
                        .select(qFollow.toUser.id)
                        .from(qFollow)
                        .where(qFollow.fromUser.id.eq(principalId))))
                .orderBy(qImage.id.desc())
                .limit(imageSearch.getSize())
                .offset(imageSearch.getOffset())
                .fetch();
    }
}
