package com.yonghyeon.ingram.domain.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long>, ImageRepositoryCustom {

    @Query(value ="SELECT i.* FROM image i JOIN " +
            "(SELECT image_id, COUNT(image_id) likeCount FROM likes GROUP BY image_id) c " +
            "ON i.id = c.image_id ORDER BY likeCount DESC", nativeQuery = true)
    List<Image> mPopularImages();
}
