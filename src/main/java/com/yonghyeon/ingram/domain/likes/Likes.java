package com.yonghyeon.ingram.domain.likes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yonghyeon.ingram.domain.image.Image;
import com.yonghyeon.ingram.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

// Maria DB와 MySql에는 Like가 키워드라서 테이블 생성이 안됨
@NoArgsConstructor
@Getter
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "likes_uk",
                        columnNames = {"user_Id", "image_Id"}
                )
        }
)
@Entity
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 즉시로딩이 기본값이기 때문에 무한참조를 방지해야함
    @JsonIgnoreProperties({"images"})
    @ManyToOne
    private User user;

    //@JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Image image;

    private LocalDateTime createDate;

    // 네이티브쿼리를 사용하면 무의미
    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}