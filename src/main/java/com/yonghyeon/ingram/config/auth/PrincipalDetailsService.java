package com.yonghyeon.ingram.config.auth;

import com.yonghyeon.ingram.domain.user.User;
import com.yonghyeon.ingram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 패스워드는 알아서 체킹
    // 리턴이 성공하면 자동으로 UserDetails 타입을 세션으로 만듦
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userEntity = userRepository.findByUsername(username);
        
        if(userEntity == null) {
            return null;
        } else {
            return new PrincipalDetails(userEntity);
        }
    }
}
