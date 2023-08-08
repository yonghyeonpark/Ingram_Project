package com.yonghyeon.ingram.service;

import com.yonghyeon.ingram.domain.user.User;
import com.yonghyeon.ingram.domain.user.UserRepository;
import com.yonghyeon.ingram.handler.CustomException;
import com.yonghyeon.ingram.web.dto.auth.SignupRequestDto;
import com.yonghyeon.ingram.web.dto.user.UserUpdateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("가입 회원 테스트")
    void test1() {

        User testUser = userRepository.findByUsername("테스트유저");

        assertThat(testUser.getEmail()).isEqualTo("test2@test.com");
        assertThat(testUser.getUsername()).isEqualTo("테스트유저");

    }

    @Test
    @DisplayName("회원 가입 에러 메시지 테스트")
    void test2() {

        SignupRequestDto dto = SignupRequestDto.builder()
                .name("테스트")
                .username("테스트유저")
                .password("1234")
                .email("test2@test.com")
                .build();

        CustomException customException = assertThrows(CustomException.class, () -> {
            authService.join(dto);
        });
        assertEquals("이미 사용중인 유저네임입니다.", customException.getMessage());
    }

}
