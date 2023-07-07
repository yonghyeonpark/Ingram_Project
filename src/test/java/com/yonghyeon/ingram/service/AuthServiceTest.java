package com.yonghyeon.ingram.service;

import com.yonghyeon.ingram.domain.user.User;
import com.yonghyeon.ingram.domain.user.UserRepository;
import com.yonghyeon.ingram.web.dto.auth.SignupRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 가입")
    void test1() {
        //given
        SignupRequestDto requestDto = SignupRequestDto.builder()
                .username("pyh")
                .password("1234")
                .email("pyh@naver.com")
                .name("pyh")
                .build();

        //when
        authService.join(requestDto);

        //then
        assertThat(userRepository.count()).isEqualTo(1L);
        User user = userRepository.findAll().get(0);
        assertThat(user.getUsername()).isEqualTo("pyh");
        assertThat(user.getEmail()).isEqualTo("pyh@naver.com");

    }
}
