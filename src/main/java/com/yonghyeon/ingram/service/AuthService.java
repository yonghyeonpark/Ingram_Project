package com.yonghyeon.ingram.service;

import com.yonghyeon.ingram.domain.user.User;
import com.yonghyeon.ingram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service // 트랜잭션 관리
public class AuthService {

    private final UserRepository userRepository;

    public User join(User user) { // user는 통신을 통해 받은 데이터를 userObject에 담은 것
        User userEntity = userRepository.save(user); // userEntity는 DB에 있는 데이터를 userObject에 담은 것
        return userEntity;
    }
}
