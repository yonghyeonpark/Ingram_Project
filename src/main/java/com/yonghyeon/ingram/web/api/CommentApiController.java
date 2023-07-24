package com.yonghyeon.ingram.web.api;

import com.yonghyeon.ingram.config.auth.PrincipalDetails;
import com.yonghyeon.ingram.domain.comment.Comment;
import com.yonghyeon.ingram.handler.CustomValidationApiException;
import com.yonghyeon.ingram.service.CommentService;
import com.yonghyeon.ingram.web.dto.CMResponseDto;
import com.yonghyeon.ingram.web.dto.comment.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/comment")
    public ResponseEntity<?> commentSave(@Valid @RequestBody CommentDto commentDto, BindingResult bindingResult,
                                         @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Comment comment = commentService.commentWrite(commentDto.getContent(), commentDto.getImageId(), principalDetails.getUser().getId());

        return new ResponseEntity<>(new CMResponseDto<>(1L, "댓글 작성 성공", comment), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> commentDelete(@PathVariable Long id) {

        commentService.commentDelete(id);

        return new ResponseEntity<>(new CMResponseDto<>(1L, "댓글 삭제 성공", null), HttpStatus.OK);
    }
}
