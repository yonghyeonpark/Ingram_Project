package com.yonghyeon.ingram.service;

import com.yonghyeon.ingram.domain.follow.FollowRepository;
import com.yonghyeon.ingram.domain.user.User;
import com.yonghyeon.ingram.domain.user.UserRepository;
import com.yonghyeon.ingram.handler.CustomException;
import com.yonghyeon.ingram.handler.CustomValidationApiException;
import com.yonghyeon.ingram.web.dto.user.UserProfileDto;
import com.yonghyeon.ingram.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FollowRepository followRepository;

    @Transactional(readOnly = true)
    public UserProfileDto userProfile(Long pageUserId, Long principalId) {

        User user = userRepository.findById(pageUserId).
                orElseThrow(() -> new CustomException("존재하지 않는 사용자입니다."));

        Long followState = followRepository.mFollowState(principalId, pageUserId);
        Long followingCount = followRepository.mFollowingCount(pageUserId);
        Long followerCount = followRepository.mFollowerCount(pageUserId);

        UserProfileDto dto = UserProfileDto.builder()
                .user(user)
                .pageOwnerState(pageUserId == principalId)// true면 주인, false는 방문자
                .imageCount(user.getImages().size())
                .followerCount(followerCount)
                .followingCount(followingCount)
                .followState(followState == 1)
                .build();

        return dto;
    }

    @Transactional
    public User userUpdate(Long id, UserUpdateDto updateDto) {
        // 영속화
        User user = userRepository.findById(id)
            .orElseThrow(() -> new CustomValidationApiException("존재하지 않는 id입니다.")); // get() - 무조건 찾았음, orElseThrow - 못찾아서 익셉션 발동

        // 영속화된 오브젝트를 수정 -> 더티체킹(업데이트 완료)
        String rawPassword = updateDto.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        user.update(encPassword, updateDto.getName(), updateDto.getPhonenum(), updateDto.getGender(), updateDto.getWebsite(), updateDto.getBio());

        return user;
        // 더티체킹이 일어나서 업데이트가 완료됨
    }

}