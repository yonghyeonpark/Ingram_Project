package com.yonghyeon.ingram.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // JPA query method
    User findByUsername(String username);
    User findByEmail(String email);

}
