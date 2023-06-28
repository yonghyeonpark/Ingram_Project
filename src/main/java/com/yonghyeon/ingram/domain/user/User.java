package com.yonghyeon.ingram.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity // DB에 테이블 생성
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 DB를 따라감
    private Long id;

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
    private String role; // 권한 -> ROLE_USER, ROLE_ADMIN

    private LocalDateTime createDate;

    @PrePersist // DB에 값이 insert 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
