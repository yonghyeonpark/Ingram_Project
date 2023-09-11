package com.yonghyeon.ingram.domain.follow;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yonghyeon.ingram.domain.user.User;
import com.yonghyeon.ingram.domain.user.UserRepository;
import com.yonghyeon.ingram.handler.CustomException;
import com.yonghyeon.ingram.web.dto.follow.FollowDto;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.*;
import static com.yonghyeon.ingram.domain.follow.QFollow.follow;
import static com.yonghyeon.ingram.domain.user.QUser.user;

@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final UserRepository userRepository;
    private final EntityManager em;

    @Override
    public void mFollow(Long fromUserId, Long toUserId) {

        User findFromUser = userRepository.findById(fromUserId).
                orElseThrow(() -> new CustomException("존재하지 않는 사용자입니다."));

        User findToUser = userRepository.findById(toUserId).
                orElseThrow(() -> new CustomException("존재하지 않는 사용자입니다."));

        Follow follow = new Follow(findFromUser, findToUser);

        em.persist(follow);
        em.flush();
    }

    @Override
    public void mUnFollow(Long fromUserId, Long toUserId) {

        jpaQueryFactory
                .delete(follow)
                .where(
                        follow.fromUser.id.eq(fromUserId),
                        follow.toUser.id.eq(toUserId))
                .execute();
    }

    @Override
    public Long mFollowState(Long principalId, Long pageUserId) {

        return jpaQueryFactory
                .select(follow.count())
                .from(follow)
                .where(follow.fromUser.id.eq(principalId), follow.toUser.id.eq(pageUserId))
                .fetchOne();
    }

    @Override
    public Long mFollowingCount(Long pageUserId) {

        return jpaQueryFactory
                .select(follow.count())
                .from(follow)
                .where(follow.fromUser.id.eq(pageUserId))
                .fetchOne();
    }

    @Override
    public Long mFollowerCount(Long pageUserId) {

        return jpaQueryFactory
                .select(follow.count())
                .from(follow)
                .where(follow.toUser.id.eq(pageUserId))
                .fetchOne();
    }

    @Override
    public List<FollowDto> mFollowingList(Long principalId, Long pageUserId) {

        // 스칼라 서브쿼리 활용
        return jpaQueryFactory.select(Projections.fields(FollowDto.class,
                        user.id.as("userId"),
                        user.username,
                        user.profileImageUrl,
                        ExpressionUtils.as(
                                select(follow.fromUser.count())
                                .from(follow)
                                .where(follow.fromUser.id.eq(principalId), follow.toUser.id.eq(user.id)) , "followState"),
                        ExpressionUtils.as(
                                select(follow.fromUser.count())
                                .from(follow)
                                .where(user.id.eq(principalId), follow.fromUser.id.eq(pageUserId), follow.toUser.id.eq(principalId)),"mirrorState")))
                .from(user)
                .innerJoin(follow)
                .on(user.id.eq(follow.toUser.id))
                .where(follow.fromUser.id.eq(pageUserId))
                .fetch();
    }

    @Override
    public List<FollowDto> mFollowerList(Long principalId, Long pageUserId) {

        return jpaQueryFactory.select(Projections.fields(FollowDto.class,
                        user.id.as("userId"),
                        user.username,
                        user.profileImageUrl,
                        ExpressionUtils.as(
                                select(follow.fromUser.count())
                                .from(follow)
                                .where(follow.fromUser.id.eq(principalId), follow.toUser.id.eq(user.id)) , "followState"),
                        ExpressionUtils.as(
                                select(follow.fromUser.count())
                                .from(follow)
                                .where(user.id.eq(principalId), follow.fromUser.id.eq(pageUserId), follow.toUser.id.eq(principalId)),"mirrorState")))
                .from(user)
                .innerJoin(follow)
                .on(user.id.eq(follow.fromUser.id))
                .where(follow.toUser.id.eq(pageUserId))
                .fetch();
    }

}
