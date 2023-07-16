package com.yonghyeon.ingram.domain.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Modifying
    @Query(value = "INSERT INTO likes(user_Id, image_Id, createDate) VALUES(:principalId, :imageId, now())", nativeQuery = true)
    void mLikes(Long principalId, Long imageId);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE user_Id = :principalId AND image_Id = :imageId", nativeQuery = true)
    void mUnLikes(Long principalId, Long imageId);
}
