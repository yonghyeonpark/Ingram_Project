package com.yonghyeon.ingram.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonghyeon.ingram.domain.image.Image;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity // DB에 테이블 생성
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 DB를 따라감
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    private String phonenum;
    private String gender;

    private String website;
    private String bio; // 자기 소개
    private String profileImageUrl; // 프사

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    private String role; // 권한 -> ROLE_USER, ROLE_GOOGLE_USER

    private LocalDateTime createDate;

    // 양방향 매핑
    // 연관관계의 주인이 아니므로 테이블에 칼럼 생성 제한
    // User를 select할 때 해당 User id로 등록된 image들을 다 가져옴(fetch = FetchType.EAGER일 때)
    // @OneToMany는 기본 설정이 fetch = FetchType.LAZY => User를 SELECT할 때 User id에 해당하는 image들을 가져오지 않고 대신 .getImages()함수가 호출될 때 가져옴
    // FetchType.EAGER는 User를 SELECT할 때 User id에 해당하는 image들을 전부 JOIN해서 가져옴
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user"}) // Image의 user필드는 무시하고 파싱(무한 참조 방지)
    private List<Image> images;

    @PrePersist // DB에 값이 insert 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    @Builder
    public User(String username, String password, String email, String name, String phonenum, String gender, String website, String bio) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.phonenum = phonenum;
        this.gender = gender;
        this.website = website;
        this.bio = bio;
    }

    public void update(String name, String username, String email, String phonenum, String gender, String website, String bio) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phonenum = phonenum;
        this.gender = gender;
        this.website = website;
        this.bio = bio;
    }

    public void passwordUpdate(String password) {
        this.password = password;
    }

}
