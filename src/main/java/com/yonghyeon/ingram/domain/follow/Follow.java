package com.yonghyeon.ingram.domain.follow;

import com.yonghyeon.ingram.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
// 복합키 유니크 제약조건 설정
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "follow_uk",
                        columnNames = {"fromUser_Id","toUser_Id"}
                )
        }
)

@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User fromUser;

    @ManyToOne
    private User toUser;

    private LocalDateTime createDate;

    @PrePersist // DB에 값이 insert 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
