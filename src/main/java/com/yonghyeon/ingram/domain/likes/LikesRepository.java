package com.yonghyeon.ingram.domain.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Modifying
    @Query(value = "INSERT INTO likes(user_id, image_id, createDate) VALUES(:principalId, :imageId, now())", nativeQuery = true)
    void mLikes(Long principalId, Long imageId);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE user_id = :principalId AND image_id = :imageId", nativeQuery = true)
    void mUnLikes(Long principalId, Long imageId);
}
