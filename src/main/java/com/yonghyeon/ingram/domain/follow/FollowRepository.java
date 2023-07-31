package com.yonghyeon.ingram.domain.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FollowRepository extends JpaRepository<Follow, Long>, FollowRepositoryCustom {

    @Modifying
    @Query(value = "INSERT INTO follow(fromUser_id, toUser_id, createDate) VALUES(:fromUserId, :toUserId, now())", nativeQuery = true)
    void mFollow(Long fromUserId, Long toUserId);

    @Modifying
    @Query(value = "DELETE FROM follow WHERE fromUser_id = :fromUserId AND toUser_id = :toUserId", nativeQuery = true)
    void mUnFollow(Long fromUserId, Long toUserId);

}
