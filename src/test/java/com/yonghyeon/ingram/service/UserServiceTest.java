package com.yonghyeon.ingram.service;

import com.yonghyeon.ingram.domain.user.User;
import com.yonghyeon.ingram.domain.user.UserRepository;
import com.yonghyeon.ingram.web.dto.user.UserPasswordUpdateDto;
import com.yonghyeon.ingram.web.dto.user.UserUpdateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @DisplayName("회원 정보 수정 테스트")
    void test1() {

        User testUser = userRepository.findByUsername("테스트유저");
        Long id = testUser.getId();

        UserUpdateDto updateDto = UserUpdateDto.builder()
                .name("테스트2")
                .username("테스트유저")
                .email("test2@test.com")
                .build();

        User user = userService.userUpdate(id, updateDto);

        assertThat(user.getName()).isEqualTo("테스트2");
    }

    /*@Test
    @DisplayName("회원 비밀번호 수정 테스트")
    void test2() {

        User testUser = userRepository.findByUsername("테스트유저");
        Long id = testUser.getId();

        UserPasswordUpdateDto updateDto = UserPasswordUpdateDto.builder()
                .nowPasswordCheck("1234")
                .newPassword("123")
                .newPasswordCheck("123")
                .build();

        User user = userService.userPasswordUpdate(id, updateDto);

        assertThat(bCryptPasswordEncoder.matches("123", user.getPassword())).isEqualTo(true);
    }*/
}
