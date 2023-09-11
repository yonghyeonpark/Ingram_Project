package com.yonghyeon.ingram.config;

import com.yonghyeon.ingram.config.oauth.OAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity // 해당 파일로 시큐리티를 활성화
@Configuration
public class SecurityConfig {

    private final OAuth2DetailsService oAuth2DetailsService;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable(); // csrf토큰 비활성화

        http.authorizeRequests()
                .antMatchers("/","/user/**","/image/**","/follow/**","/comment/**", "/api/**").authenticated() // 1. 해당 url에 대한 요청은 인증이 필요
                .anyRequest().permitAll() // 2. 외에 나머지 요청은 허용
                .and()
                .formLogin()
                .loginPage("/auth/signin") // 3. GET(페이지 요청), 권한없는 페이지로 갔을 때 자동으로 redirection
                .loginProcessingUrl("/auth/signin") // 4. POST(로그인 요청), 스프링 시큐리티가 로그인 프로세스 진행
                .defaultSuccessUrl("/") // 5. 로그인이 정상적으로 됐을 때의 경로 설정
                .and()
                .oauth2Login() // form 로그인에 더하여 oauth2 로그인까지 가능하도록 설정
                .userInfoEndpoint() // 인증 코드를 받지 않고(코드받고 엑세스 토큰을 받는 건 시큐리티가 알아서 처리) 바로 최종응답(회원정보)을  받음
                .userService(oAuth2DetailsService);

        return http.build();
    }
}
