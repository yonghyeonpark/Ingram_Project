package com.yonghyeon.ingram.service;

import com.yonghyeon.ingram.domain.comment.Comment;
import com.yonghyeon.ingram.domain.comment.CommentRepository;
import com.yonghyeon.ingram.domain.image.Image;
import com.yonghyeon.ingram.domain.user.User;
import com.yonghyeon.ingram.domain.user.UserRepository;
import com.yonghyeon.ingram.handler.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public Comment commentWrite(String content, Long imageId, Long principalId) {

        // 가짜 객체 생성
        // 객체를 만들 때 id값만 담아서 insert
        Image image = new Image();
        image.setId(imageId);

        /*User user = new User();
        user.setId(principalId);*/

        User userEntity = userRepository.findById(principalId)
                .orElseThrow(() -> new CustomApiException("존재하지 않는 사용자 입니다."));

        Comment comment = Comment.builder()
                .content(content)
                .image(image)
                .user(userEntity)
                .build();

        return commentRepository.save(comment);
    }

    @Transactional
    public void commentDelete(Long id) {
        try {
            commentRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomApiException(e.getMessage());
        }
    }
}
