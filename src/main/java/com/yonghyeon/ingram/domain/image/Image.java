package com.yonghyeon.ingram.domain.image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonghyeon.ingram.domain.likes.Likes;
import com.yonghyeon.ingram.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caption;
    private String postImageUrl; // 사진을 전송 받아서 그 사진을 서버에 특정 폴더에 저장 - DB에 그 저장된 경로를 insert

    @JsonIgnoreProperties({"images"})
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    private LocalDateTime createDate;

    /*@OneToMany(mappedBy = "image")
    private List<Likes> likes;*/

    // javax.persistence => DB에 컬럼 생성 제한
    /*@Transient
    private boolean likeState;

    public void setLikeState(boolean likeState) {
        this.likeState = likeState;
    }*/

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    @Builder
    public Image(String caption, String postImageUrl, User user) {
        this.caption = caption;
        this.postImageUrl = postImageUrl;
        this.user = user;
    }
}
