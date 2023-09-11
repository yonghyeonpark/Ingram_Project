package com.yonghyeon.ingram.domain.likes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yonghyeon.ingram.domain.image.Image;
import com.yonghyeon.ingram.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

// Maria DB와 MySql에는 Like가 키워드이므로 Likes로 설정
@NoArgsConstructor
@Getter
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "likes_uk",
                        columnNames = {"user_id", "image_id"}
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

    @ManyToOne(fetch = FetchType.LAZY)
    private Image image;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    public Likes(User user, Image image) {
        this.user = user;
        this.image = image;
    }
}
