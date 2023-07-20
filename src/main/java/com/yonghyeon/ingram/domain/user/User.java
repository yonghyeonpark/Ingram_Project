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

    public void setId(Long id) {
        this.id = id;
    }

    @Column(length = 10, unique = true, nullable = false)
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

    private String role; // 권한 -> ROLE_USER, ROLE_ADMIN

    private LocalDateTime createDate;

    // 양방향 매핑
    // 연관관계의 주인이 아니므로 테이블에 칼럼 생성 제한
    // User를 select할 때 해당 User id로 등록된 image들을 다 가져옴
    // @OneToManey는 기본 설정이 fetch = FetchType.LAZY => User를 select할 때 User id에 해당하는 image들을 가져오지 않고 대신 .getImages()함수의 image들이 호출할 때 가져옴
    // FetchType.EAGER는 User를 select할 때 User id에 해당하는 image들을 가져옴
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user"}) // Image의 user필드는 무시하고 파싱(무한 참조 방지)
    private List<Image> images;

    @PrePersist // DB에 값이 insert 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    public void update(String password, String name, String phonenum, String gender, String website, String bio) {
        this.password = password;
        this.name = name;
        this.phonenum = phonenum;
        this.gender = gender;
        this.website = website;
        this.bio = bio;
    }

}
