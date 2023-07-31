package com.yonghyeon.ingram.service;

import com.yonghyeon.ingram.domain.follow.FollowRepository;
import com.yonghyeon.ingram.domain.user.User;
import com.yonghyeon.ingram.domain.user.UserRepository;
import com.yonghyeon.ingram.handler.CustomApiException;
import com.yonghyeon.ingram.handler.CustomException;
import com.yonghyeon.ingram.handler.CustomValidationApiException;
import com.yonghyeon.ingram.web.dto.user.UserPasswordUpdateDto;
import com.yonghyeon.ingram.web.dto.user.UserProfileDto;
import com.yonghyeon.ingram.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

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
                .pageOwnerState(pageUserId == principalId)// true는 주인, false는 방문자
                .imageCount(user.getImages().size())
                .followerCount(followerCount)
                .followingCount(followingCount)
                .followState(followState == 1)
                .userState(user.getRole().equals("ROLE_USER")) // true는 일반, false는 OAuth2
                .build();

        // 좋아요 카운트 추가
        user.getImages().forEach((image)-> {
            image.setLikeCount(image.getLikes().size());
        });

        return dto;
    }

    @Transactional
    public User userUpdate(Long id, UserUpdateDto updateDto) {
        // 영속화
        User user = userRepository.findById(id)
            .orElseThrow(() -> new CustomValidationApiException("존재하지 않는 id입니다.")); // get() - 무조건 찾았음, orElseThrow - 못찾아서 익셉션 발동

        if(userRepository.findByUsername(updateDto.getUsername())!=null) {
            throw new CustomApiException("이미 존재하는 유저네임 입니다.");
        }

        // 영속화된 오브젝트를 수정 -> 더티체킹(업데이트 완료)
        user.update(updateDto.getName(), updateDto.getUsername(), updateDto.getPhonenum(), updateDto.getGender(), updateDto.getWebsite(), updateDto.getBio());

        return user;
        // 더티체킹이 일어나서 업데이트가 완료됨
    }

    @Transactional
    public User userPasswordUpdate(Long id, UserPasswordUpdateDto updateDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomValidationApiException("존재하지 않는 id입니다."));

        if(!updateDto.getNewPassword().equals(updateDto.getNewPasswordCheck())) {
            throw new CustomApiException("새 비밀번호가 일치하지 않습니다.");
        }
        if(!bCryptPasswordEncoder.matches(updateDto.getNowPasswordCheck(), user.getPassword())) {
            throw new CustomApiException("현재 비밀번호가 일치하지 않습니다.");
        }
            String rawPassword = updateDto.getNewPassword();
            String encPassword = bCryptPasswordEncoder.encode(rawPassword);

            user.passwordUpdate(encPassword);

            return user;
    }

    @Value("${file.path}") // file.경로명
    private String uploadFolder;
    @Transactional
    public User userProfileUpdate(Long principalId, MultipartFile profileImageFile) {

        // 동일한 파일명이 이미 들어와 있는 파일을 덮는 것을 방지하기위해 UUID 사용(유일성 보장)
        UUID uuid = UUID.randomUUID();
        // 극악의 확률로 UUID가 같을 것을 대비해 파일명과 조합
        String filename = uuid + "_" + profileImageFile.getOriginalFilename();// 실제 파일명

        Path imageFilePath = Paths.get(uploadFolder + filename);

        // 통신(외부에서 data 받아옴), I/O(하드디스크에 write, read) => 예외 발생 가능(읽으려는 파일이 존재하지 않을 때) / 컴파일에러가 아닌 런타임에러 => 따라서 예외를 잡아줘야 함
        try {
            Files.write(imageFilePath, profileImageFile.getBytes()); // 파일경로, 실제파일
        } catch (Exception e) {
            e.printStackTrace();
        }

        User user = userRepository.findById(principalId)
                .orElseThrow(() -> new CustomApiException("존재하지 않는 id 입니다."));

        user.setProfileImageUrl(filename);

        return user;
    }

}