package com.yonghyeon.ingram.domain.bookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long>, BookmarkRepositoryCustom {

    @Modifying
    @Query(value = "INSERT INTO bookmark(user_id, image_id, createDate) VALUES(:principalId, :imageId, now())", nativeQuery = true)
    void mBookmark(Long principalId, Long imageId);

    @Modifying
    @Query(value = "DELETE FROM bookmark WHERE user_id = :principalId AND image_id = :imageId", nativeQuery = true)
    void mUnBookmark(Long principalId, Long imageId);

}
