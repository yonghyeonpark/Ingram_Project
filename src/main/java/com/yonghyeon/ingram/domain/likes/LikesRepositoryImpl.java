package com.yonghyeon.ingram.domain.likes;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yonghyeon.ingram.domain.follow.QFollow;
import com.yonghyeon.ingram.domain.image.Image;
import com.yonghyeon.ingram.domain.image.ImageRepository;
import com.yonghyeon.ingram.domain.image.ImageRepositoryCustom;
import com.yonghyeon.ingram.domain.image.QImage;
import com.yonghyeon.ingram.domain.user.QUser;
import com.yonghyeon.ingram.domain.user.User;
import com.yonghyeon.ingram.domain.user.UserRepository;
import com.yonghyeon.ingram.handler.CustomException;
import com.yonghyeon.ingram.web.dto.image.ImageSearch;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.yonghyeon.ingram.domain.image.QImage.*;
import static com.yonghyeon.ingram.domain.likes.QLikes.*;
import static com.yonghyeon.ingram.domain.user.QUser.*;

@RequiredArgsConstructor
public class LikesRepositoryImpl implements LikesRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    @Override
    public void mLikes(Long principalId, Long imageId) {

        User findUser = userRepository.findById(principalId).
                orElseThrow(() -> new CustomException("존재하지 않는 사용자입니다."));

        Image findImage = imageRepository.findById(imageId).
                orElseThrow(() -> new CustomException("이미지가 존재하지 않습니다."));


        Likes likes = new Likes(findUser, findImage);

        em.persist(likes);
        em.flush();
    }

    @Override
    public void mUnLikes(Long principalId, Long imageId) {

        jpaQueryFactory
                .delete(likes)
                .where(
                        likes.user.id.eq(principalId),
                        likes.image.id.eq(imageId))
                .execute();
    }
}
