package com.yonghyeon.ingram.web.api;

import com.yonghyeon.ingram.config.auth.PrincipalDetails;
import com.yonghyeon.ingram.domain.image.Image;
import com.yonghyeon.ingram.service.BookmarkService;
import com.yonghyeon.ingram.service.ImageService;
import com.yonghyeon.ingram.service.LikesService;
import com.yonghyeon.ingram.web.dto.CMResponseDto;
import com.yonghyeon.ingram.web.dto.image.ImageSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ImageApiController {

    private final ImageService imageService;
    private final LikesService likesService;
    private final BookmarkService bookmarkService;

    @GetMapping("/api/image")
    public ResponseEntity<?> image(@AuthenticationPrincipal PrincipalDetails principalDetails, ImageSearch imageSearch) {

        List<Image> images = imageService.getImages(principalDetails.getUser().getId(), imageSearch);

        return new ResponseEntity<>(new CMResponseDto<>(1L, "이미지 리스트 가져오기 성공", images), HttpStatus.OK);
    }

    @PostMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> likes(@PathVariable Long imageId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        likesService.likes(principalDetails.getUser().getId(), imageId);

        return new ResponseEntity<>(new CMResponseDto<>(1L, "이미지 좋아요 성공", null), HttpStatus.CREATED); // 201 코드
    }

    @DeleteMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> unLikes(@PathVariable Long imageId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        likesService.unLikes(principalDetails.getUser().getId(), imageId);

        return new ResponseEntity<>(new CMResponseDto<>(1L, "이미지 좋아요 취소 성공", null), HttpStatus.OK);
    }

    @PostMapping("/api/image/{imageId}/bookmark")
    public ResponseEntity<?> bookmark(@PathVariable Long imageId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        bookmarkService.bookmark(principalDetails.getUser().getId(), imageId);

        return new ResponseEntity<>(new CMResponseDto<>(1L, "이미지 북마크 성공", null), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/image/{imageId}/bookmark")
    public ResponseEntity<?> unBookmark(@PathVariable Long imageId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        bookmarkService.unBookmark(principalDetails.getUser().getId(), imageId);

        return new ResponseEntity<>(new CMResponseDto<>(1L, "이미지 북마크 취소 성공", null), HttpStatus.OK);
    }

}
