package com.yonghyeon.ingram.service;

import com.yonghyeon.ingram.domain.likes.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;

    @Transactional
    public void likes(Long principalId, Long imageId) {
        likesRepository.mLikes(principalId, imageId);
    }

    @Transactional
    public void unLikes(Long principalId, Long imageId) {
        likesRepository.mUnLikes(principalId, imageId);
    }

}
