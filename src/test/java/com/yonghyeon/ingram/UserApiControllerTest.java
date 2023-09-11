package com.yonghyeon.ingram;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonghyeon.ingram.domain.user.User;
import com.yonghyeon.ingram.domain.user.UserRepository;
import com.yonghyeon.ingram.web.dto.user.UserUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:db/teardown.sql")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class UserApiControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp(){
        User testUser = User.builder()
                .name("테스트")
                .username("테스트유저")
                .password(passwordEncoder.encode("1234"))
                .email("test@naver.com")
                .build();

        userRepository.save(testUser);
        em.clear();
    }

    @WithUserDetails(value = "테스트유저", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    @DisplayName("/api/user/{id}")
    void test1() throws Exception {

        User testUser = userRepository.findByUsername("테스트유저");
        Long id = testUser.getId();
        System.out.println("테스트 : id : "+id);

        UserUpdateDto updateDto = UserUpdateDto.builder()
                .name("테스트2")
                .username("테스트유저")
                .email("test@naver.com")
                .build();

        mockMvc.perform(put("/api/user/{id}", id)
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("name", updateDto.getName())
                        .param("username", updateDto.getUsername())
                        .param("email", updateDto.getEmail()))
                .andExpect(status().isOk())
                .andDo(print());

        System.out.println();
    }
}
