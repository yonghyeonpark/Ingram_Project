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
        // 객체를 만들 때 id값만 담아서 insert 할 수 있다.
        // return 시에 image 객체와 user 객체는 id 값만 가지고 있는 빈 객체를 리턴 받는다.(가짜 객체를 생성했기 때문에)
        // 진짜 객체를 가져오기 위해서는 image와 user의 repository를 이용해야함
        Image image = new Image();
        image.setId(imageId);

        /*User user = new User();
        user.setId(principalId);*/

        User user = userRepository.findById(principalId)
                .orElseThrow(() -> new CustomApiException("존재하지 않는 사용자 입니다."));

        Comment comment = Comment.builder()
                .content(content)
                .image(image)
                .user(user)
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
