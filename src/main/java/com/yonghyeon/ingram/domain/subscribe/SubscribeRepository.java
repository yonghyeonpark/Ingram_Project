package com.yonghyeon.ingram.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    @Modifying
    @Query(value = "INSERT INTO subscribe(fromUser_Id, toUser_Id, createDate) VALUES(:fromUserId, :toUserId, now())", nativeQuery = true)
    void mSubscribe(Long fromUserId, Long toUserId);

    @Modifying
    @Query(value = "DELETE FROM subscribe WHERE fromUser_Id = :fromUserId AND toUser_Id = :toUserId", nativeQuery = true)
    void mUnSubscribe(Long fromUserId, Long toUserId);
}
