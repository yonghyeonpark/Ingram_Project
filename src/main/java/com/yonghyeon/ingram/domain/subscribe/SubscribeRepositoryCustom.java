package com.yonghyeon.ingram.domain.subscribe;

public interface SubscribeRepositoryCustom {

    void mSubscribe(Long fromUserId, Long toUserId);

    //Long mUnSubscribe(Long fromUserId, Long toUserId);
}
