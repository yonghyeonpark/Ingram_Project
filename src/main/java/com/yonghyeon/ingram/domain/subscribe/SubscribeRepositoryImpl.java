package com.yonghyeon.ingram.domain.subscribe;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SubscribeRepositoryImpl implements SubscribeRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void mSubscribe(Long fromUserId, Long toUserId) {
        QSubscribe qSubscribe = QSubscribe.subscribe;

        jpaQueryFactory.insert(qSubscribe)
                .columns(qSubscribe.fromUser, qSubscribe.toUser)
                .values(fromUserId, toUserId)
                .execute();
    }

    /*@Override
    public Long mUnSubscribe(Long fromUserId, Long toUserId) {
        return null;
    }*/
}
