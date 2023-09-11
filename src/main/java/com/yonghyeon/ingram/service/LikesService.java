package com.yonghyeon.ingram.service;

import com.yonghyeon.ingram.domain.image.Image;
import com.yonghyeon.ingram.domain.image.ImageRepository;
import com.yonghyeon.ingram.domain.likes.Likes;
import com.yonghyeon.ingram.domain.likes.LikesRepository;
import com.yonghyeon.ingram.domain.user.User;
import com.yonghyeon.ingram.domain.user.UserRepository;
import com.yonghyeon.ingram.handler.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final EntityManager em;

    @Transactional
    public void likes(Long principalId, Long imageId) {
        likesRepository.mLikes(principalId, imageId);
    }


    @Transactional
    public void unLikes(Long principalId, Long imageId) {
        likesRepository.mUnLikes(principalId, imageId);
    }

}
