package com.yonghyeon.ingram.domain.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FollowRepository extends JpaRepository<Follow, Long>, FollowRepositoryCustom {

    @Modifying
    @Query(value = "INSERT INTO follow(fromUser_Id, toUser_Id, createDate) VALUES(:fromUserId, :toUserId, now())", nativeQuery = true)
    void mFollow(Long fromUserId, Long toUserId);

    @Modifying
    @Query(value = "DELETE FROM follow WHERE fromUser_Id = :fromUserId AND toUser_Id = :toUserId", nativeQuery = true)
    void mUnFollow(Long fromUserId, Long toUserId);

}
