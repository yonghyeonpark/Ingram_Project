package com.yonghyeon.ingram.domain.follow;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long mFollowState(Long principalId, Long pageUserId) {

        QFollow qFollow = QFollow.follow;

        return jpaQueryFactory
                .select(qFollow.count())
                .from(qFollow)
                .where(qFollow.fromUser.id.eq(principalId), qFollow.toUser.id.eq(pageUserId))
                .fetchOne();
    }

    @Override
    public Long mFollowingCount(Long pageUserId) {

        QFollow qFollow = QFollow.follow;

        return jpaQueryFactory
                .select(qFollow.count())
                .from(qFollow)
                .where(qFollow.fromUser.id.eq(pageUserId))
                .fetchOne();
    }

    @Override
    public Long mFollowerCount(Long pageUserId) {

        QFollow qFollow = QFollow.follow;

        return jpaQueryFactory
                .select(qFollow.count())
                .from(qFollow)
                .where(qFollow.toUser.id.eq(pageUserId))
                .fetchOne();
    }

}
