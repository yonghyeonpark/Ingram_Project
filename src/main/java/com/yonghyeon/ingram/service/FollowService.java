package com.yonghyeon.ingram.service;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yonghyeon.ingram.domain.follow.FollowRepository;
import com.yonghyeon.ingram.domain.follow.QFollow;
import com.yonghyeon.ingram.domain.user.QUser;
import com.yonghyeon.ingram.handler.CustomApiException;
import com.yonghyeon.ingram.web.dto.follow.FollowDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Transactional(readOnly = true)
    public List<FollowDto> followingList(Long principalId, Long pageUserId) {

        QFollow qFollow = QFollow.follow;
        QUser qUser = QUser.user;

        // 스칼라 서브쿼리 활용
        return jpaQueryFactory.select(Projections.bean(FollowDto.class,
                        qUser.id.as("userId"),
                        qUser.username,
                        qUser.profileImageUrl,
                        ExpressionUtils.as(JPAExpressions.select(qFollow.fromUser.id.eq(principalId), qFollow.toUser.id.eq(qUser.id)) , "followState"),
                        ExpressionUtils.as(JPAExpressions.select(qUser.id.eq(principalId)),"mirrorState")))
                .from(qUser)
                .join(qFollow)
                .on(qUser.id.eq(qFollow.toUser.id))
                .where(qFollow.fromUser.id.eq(pageUserId))
                .fetch();

    }


    @Transactional
    public void follow(Long fromUserId, Long toUserId) {
        try {
            followRepository.mFollow(fromUserId, toUserId);
        } catch (Exception e) {
            throw new CustomApiException("이미 팔로우중입니다.");
        }

    }

    @Transactional
    public void unFollow(Long fromUserId, Long toUserId) {
        followRepository.mUnFollow(fromUserId, toUserId);
    }

}
