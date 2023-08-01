package com.yonghyeon.ingram.service;

import com.yonghyeon.ingram.domain.user.User;
import com.yonghyeon.ingram.domain.user.UserRepository;
import com.yonghyeon.ingram.handler.CustomException;
import com.yonghyeon.ingram.web.dto.auth.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service // 트랜잭션 관리
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional // 조회가 아닌 (insert, update, delete)에 적용
    public User join(SignupRequestDto requestDto) { // user는 통신을 통해 받은 데이터를 userObject에 담은 것

        if(userRepository.findByUsername(requestDto.getUsername())!=null) {
            throw new CustomException("이미 사용중인 유저네임입니다.");
        }

        if(userRepository.findByEmail(requestDto.getEmail())!=null) {
            throw new CustomException("이미 사용중인 이메일입니다.");
        }

        String rawPassword = requestDto.getPassword();
        String encoPassword = passwordEncoder.encode(rawPassword);

        User user = User.builder()
                .username(requestDto.getUsername())
                .password(encoPassword)
                .email(requestDto.getEmail())
                .name(requestDto.getName())
                .role("ROLE_USER")
                .build();

        User userEntity = userRepository.save(user); // userEntity는 DB에 있는 데이터를 userObject에 담은 것
        return userEntity;
    }
}
