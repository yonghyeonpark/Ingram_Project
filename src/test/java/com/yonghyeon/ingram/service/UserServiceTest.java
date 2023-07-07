package com.yonghyeon.ingram.service;

import com.yonghyeon.ingram.domain.user.User;
import com.yonghyeon.ingram.domain.user.UserRepository;
import com.yonghyeon.ingram.web.dto.user.UserUpdateDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    /*@Test
    @DisplayName("회원 정보 수정")
    void test1() {
        //given
        User user = User.builder()
                .username("유저이름")
                .name("이름")
                .password("비번")
                .email("이메일@이메일")
                .build();

        userRepository.save(user);

        UserUpdateDto updateDto = UserUpdateDto.builder()
                .name("이름")
                .password("비번")
                .gender("Male")
                .build();

        //when
        userService.userUpdate(user.getId(), updateDto);

        User changeUser = userRepository.findById(user.getId()).get();


        //then
        assertThat(changeUser.getGender()).isEqualTo("Male");

    }*/
}
