package com.yonghyeon.ingram.web.api;

import com.yonghyeon.ingram.config.auth.PrincipalDetails;
import com.yonghyeon.ingram.domain.user.User;
import com.yonghyeon.ingram.handler.CustomValidationApiException;
import com.yonghyeon.ingram.service.FollowService;
import com.yonghyeon.ingram.service.UserService;
import com.yonghyeon.ingram.web.dto.CMResponseDto;
import com.yonghyeon.ingram.web.dto.follow.FollowDto;
import com.yonghyeon.ingram.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final FollowService followService;

    @GetMapping("/api/user/{pageUserId}/following")
    public ResponseEntity<?> followingList(@PathVariable Long pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        List<FollowDto> followDtos = followService.followingList(principalDetails.getUser().getId(), pageUserId);

        return new ResponseEntity<>(new CMResponseDto<>(1L, "팔로잉 리스트 가져오기 성공", followDtos), HttpStatus.OK);
    }

    @GetMapping("/api/user/{pageUserId}/follower")
    public ResponseEntity<?> followerList(@PathVariable Long pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        List<FollowDto> followDtos = followService.followerList(principalDetails.getUser().getId(), pageUserId);

        return new ResponseEntity<>(new CMResponseDto<>(1L, "팔로워 리스트 가져오기 성공", followDtos), HttpStatus.OK);
    }

    @PutMapping("/api/user/{id}")
    public CMResponseDto<?> update(
            @PathVariable Long id,
            @Valid UserUpdateDto updateDto,
            BindingResult bindingResult, // 꼭 @Valid 다음 파라미터에 와야함
            @AuthenticationPrincipal PrincipalDetails principalDetails /*, Model model*/) {
        //model.addAttribute("principal", principalDetails.getUser());

            User user = userService.userUpdate(id, updateDto);
            principalDetails.setUser(user);

            // 응답시에 user 엔터티의 모든 getter 함수가 호출되고 JSON으로 파싱하여 응답
            // 따라서 user.getImages도 호출하는데 여기서 다시 image.getUser 다시 user.getImages 이런식으로 무한 반복됨
            return new CMResponseDto<>(1L, "수정 완료", user);
    }

    @PutMapping("/api/user/{principalId}/profileImageUrl")
    // caption 필요없이 사진만 받으면 되기 때문에 dto를 받지 않음 / profile.jsp의 폼태그에 있는 input name을 정확히 매칭 시켜야 함
    public ResponseEntity<?> profileImageUpdate(
            @PathVariable Long principalId,
            MultipartFile profileImageFile,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        User user = userService.userProfileUpdate(principalId, profileImageFile);

        principalDetails.setUser(user);// 세션 변경

        return new ResponseEntity<>(new CMResponseDto<>(1L,"프로필 사진 변경 완료",null), HttpStatus.OK);
    }
}