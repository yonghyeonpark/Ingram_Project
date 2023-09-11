package com.yonghyeon.ingram.domain.follow;

import com.yonghyeon.ingram.web.dto.follow.FollowDto;

import java.util.List;

public interface FollowRepositoryCustom {

    void mFollow(Long fromUserId, Long toUserId);

    void mUnFollow(Long fromUserId, Long toUserId);

    Long mFollowState(Long principalId, Long pageUserId);

    Long mFollowingCount(Long pageUserId);

    Long mFollowerCount(Long pageUserId);

    List<FollowDto> mFollowingList(Long principalId, Long pageUserId);

    List<FollowDto> mFollowerList(Long principalId, Long pageUserId);
}
