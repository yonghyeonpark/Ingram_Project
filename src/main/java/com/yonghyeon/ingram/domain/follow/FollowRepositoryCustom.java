package com.yonghyeon.ingram.domain.follow;

import com.yonghyeon.ingram.web.dto.follow.FollowDto;

import java.util.List;

public interface FollowRepositoryCustom {

    public Long mFollowState(Long principalId, Long pageUserId);

    public Long mFollowingCount(Long pageUserId);

    public Long mFollowerCount(Long pageUserId);

    public List<FollowDto> mFollowingList(Long principalId, Long pageUserId);

    public List<FollowDto> mFollowerList(Long principalId, Long pageUserId);
}
