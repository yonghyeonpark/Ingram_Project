package com.yonghyeon.ingram.domain.likes;

import com.yonghyeon.ingram.domain.image.ImageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, Long>, LikesRepositoryCustom {

}
