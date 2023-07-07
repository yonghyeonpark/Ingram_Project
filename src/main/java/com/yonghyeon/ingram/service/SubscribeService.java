package com.yonghyeon.ingram.service;

import com.yonghyeon.ingram.domain.subscribe.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    @Transactional
    public void subscribe(Long fromUserId, Long toUserId) {
        subscribeRepository.mSubscribe(fromUserId, toUserId);
    }

    /*@Transactional
    public void unSubscribe(Long fromUserId, Long toUserId) {

    }*/

}
