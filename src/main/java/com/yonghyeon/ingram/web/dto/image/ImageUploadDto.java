package com.yonghyeon.ingram.web.dto.image;

import com.yonghyeon.ingram.domain.image.Image;
import com.yonghyeon.ingram.domain.user.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUploadDto {

    // MultipartFile은 @NotBlank 사용 불가능 => 별도 처리
    private MultipartFile file;
    private String caption;

    public Image toEntity(String postImageUrl, User user) {
        return Image.builder()
                .caption(caption)
                .postImageUrl(postImageUrl)
                .user(user)
                .build();
    }
}
