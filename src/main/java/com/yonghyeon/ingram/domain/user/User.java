package com.yonghyeon.ingram.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity // DB에 테이블 생성
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 DB를 따라감
    private Long id;

    private String username;
    private String password;
    private String email;
    private String name;
    private String phonenum;
    private String gender;

    private String website;
    private String bio; // 자기 소개
    private String profileImageUrl; // 프사
    private String role; // 권한

    private LocalDateTime createDate;

    @PrePersist // DB에 값이 insert 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    @Builder
    public User(String username, String password, String email, String name, String phonenum, String gender, String website, String bio, String profileImageUrl, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.phonenum = phonenum;
        this.gender = gender;
        this.website = website;
        this.bio = bio;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
    }
}
