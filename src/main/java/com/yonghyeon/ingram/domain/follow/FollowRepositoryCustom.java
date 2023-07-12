package com.yonghyeon.ingram.domain.follow;

public interface FollowRepositoryCustom {

    public Long mFollowState(Long principalId, Long pageUserId);

    public Long mFollowingCount(Long pageUserId);

    public Long mFollowerCount(Long pageUserId);

}
