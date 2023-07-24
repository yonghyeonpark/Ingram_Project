package com.yonghyeon.ingram.domain.follow;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yonghyeon.ingram.domain.user.QUser;
import com.yonghyeon.ingram.web.dto.follow.FollowDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

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

    @Override
    public List<FollowDto> mFollowingList(Long principalId, Long pageUserId) {

        QFollow qFollow = QFollow.follow;
        QUser qUser = QUser.user;

        // 스칼라 서브쿼리 활용
        return jpaQueryFactory.select(Projections.fields(FollowDto.class,
                        qUser.id.as("userId"),
                        qUser.username,
                        qUser.profileImageUrl,
                        ExpressionUtils.as(JPAExpressions
                                .select(qFollow.fromUser.count())
                                .from(qFollow)
                                .where(qFollow.fromUser.id.eq(principalId), qFollow.toUser.id.eq(qUser.id)) , "followState"),
                        ExpressionUtils.as(JPAExpressions
                                .select(qFollow.fromUser.count())
                                .from(qFollow)
                                .where(qUser.id.eq(principalId), qFollow.fromUser.id.eq(pageUserId), qFollow.toUser.id.eq(principalId)),"mirrorState")))
                .from(qUser)
                .innerJoin(qFollow)
                .on(qUser.id.eq(qFollow.toUser.id))
                .where(qFollow.fromUser.id.eq(pageUserId))
                .fetch();
    }

    @Override
    public List<FollowDto> mFollowerList(Long principalId, Long pageUserId) {

        QFollow qFollow = QFollow.follow;
        QUser qUser = QUser.user;

        return jpaQueryFactory.select(Projections.fields(FollowDto.class,
                        qUser.id.as("userId"),
                        qUser.username,
                        qUser.profileImageUrl,
                        ExpressionUtils.as(JPAExpressions
                                .select(qFollow.fromUser.count())
                                .from(qFollow)
                                .where(qFollow.fromUser.id.eq(principalId), qFollow.toUser.id.eq(qUser.id)) , "followState"),
                        ExpressionUtils.as(JPAExpressions
                                .select(qFollow.fromUser.count())
                                .from(qFollow)
                                .where(qUser.id.eq(principalId), qFollow.fromUser.id.eq(pageUserId), qFollow.toUser.id.eq(principalId)),"mirrorState")))
                .from(qUser)
                .innerJoin(qFollow)
                .on(qUser.id.eq(qFollow.fromUser.id))
                .where(qFollow.toUser.id.eq(pageUserId))
                .fetch();
    }

}
