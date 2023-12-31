package com.yonghyeon.ingram.domain.bookmark;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yonghyeon.ingram.domain.follow.QFollow;
import com.yonghyeon.ingram.domain.image.Image;
import com.yonghyeon.ingram.domain.image.ImageRepository;
import com.yonghyeon.ingram.domain.image.QImage;
import com.yonghyeon.ingram.domain.user.User;
import com.yonghyeon.ingram.domain.user.UserRepository;
import com.yonghyeon.ingram.handler.CustomException;
import com.yonghyeon.ingram.web.dto.image.ImageSearch;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.yonghyeon.ingram.domain.bookmark.QBookmark.*;

@RequiredArgsConstructor
public class BookmarkRepositoryImpl implements BookmarkRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final EntityManager em;

    @Override
    public void mBookmark(Long principalId, Long imageId) {
        User findUser = userRepository.findById(principalId).
                orElseThrow(() -> new CustomException("존재하지 않는 사용자입니다."));

        Image findImage = imageRepository.findById(imageId).
                orElseThrow(() -> new CustomException("이미지가 존재하지 않습니다."));

        Bookmark bookmark = new Bookmark(findUser, findImage);

        em.persist(bookmark);
        em.flush();
    }

    @Override
    public void mUnBookmark(Long principalId, Long imageId) {
        jpaQueryFactory
                .delete(bookmark)
                .where(
                        bookmark.user.id.eq(principalId),
                        bookmark.image.id.eq(imageId))
                .execute();
    }

    @Override
    public List<Image> mBookmarkImages(Long principalId) {

        return jpaQueryFactory
                .select(bookmark.image)
                .from(bookmark)
                .where(bookmark.user.id.eq(principalId))
                .fetch();
    }
}
