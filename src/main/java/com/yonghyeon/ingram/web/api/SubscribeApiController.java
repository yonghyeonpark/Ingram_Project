package com.yonghyeon.ingram.web.api;

import com.yonghyeon.ingram.config.auth.PrincipalDetails;
import com.yonghyeon.ingram.service.SubscribeService;
import com.yonghyeon.ingram.web.dto.CMResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SubscribeApiController {

    private final SubscribeService subscribeService;

    @PostMapping("/api/subscribe/{toUserId}")
    public ResponseEntity<?> subscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long toUserId){
        subscribeService.subscribe(principalDetails.getUser().getId(), toUserId);
        return new ResponseEntity<>(new CMResponseDto<>(1L, "구독 성공", null), HttpStatus.OK);
    }

    /*@DeleteMapping("/api/subscribe/{toUserId}")
    public void unSubscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long id){

    }*/

}
