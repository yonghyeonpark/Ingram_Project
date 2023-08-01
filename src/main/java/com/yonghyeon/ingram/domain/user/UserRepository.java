package com.yonghyeon.ingram.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository를 상속하면 어노테이션을 달지 않아도 IoC등록이 자동으로 된다.
public interface UserRepository extends JpaRepository<User, Long> {

    // JPA query method
    User findByUsername(String username);
    User findByEmail(String email);

}
