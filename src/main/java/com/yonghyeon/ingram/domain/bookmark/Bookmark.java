package com.yonghyeon.ingram.domain.bookmark;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonghyeon.ingram.domain.image.Image;
import com.yonghyeon.ingram.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "bookmark_uk",
                        columnNames = {"user_id", "image_id"}
                )
        }
)
@Entity
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @JsonIgnoreProperties({"bookmarks"})
    @ManyToOne
    private Image image;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    public Bookmark(User user, Image image) {
        this.user = user;
        this.image = image;
    }
}
