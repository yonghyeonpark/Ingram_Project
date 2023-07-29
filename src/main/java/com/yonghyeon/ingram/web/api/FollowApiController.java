package com.yonghyeon.ingram.web.api;

import com.yonghyeon.ingram.config.auth.PrincipalDetails;
import com.yonghyeon.ingram.service.FollowService;
import com.yonghyeon.ingram.web.dto.CMResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FollowApiController {

    private final FollowService followService;

    @PostMapping("/api/follow/{toUserId}")
    public ResponseEntity<?> follow(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long toUserId){
        followService.follow(principalDetails.getUser().getId(), toUserId);
        return new ResponseEntity<>(new CMResponseDto<>(1L, "팔로우 성공", null), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/follow/{toUserId}")
    public ResponseEntity<?> unFollow(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long toUserId){
        followService.unFollow(principalDetails.getUser().getId(), toUserId);
        return new ResponseEntity<>(new CMResponseDto<>(1L, "언팔로우 성공", null), HttpStatus.OK);
    }

}
