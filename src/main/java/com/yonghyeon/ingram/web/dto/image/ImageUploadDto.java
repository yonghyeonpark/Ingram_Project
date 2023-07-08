package com.yonghyeon.ingram.web.dto.image;

import com.yonghyeon.ingram.domain.image.Image;
import com.yonghyeon.ingram.domain.user.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUploadDto {

    private MultipartFile file; // MultipartFile은 @NotBlank 사용 불가능, @Valid/BindingResult로 처리할 수 없음 => 별도로 만들어야 함
    private String caption;

    public Image toEntity(String postImageUrl, User user) {
        return Image.builder()
                .caption(caption)
                .postImageUrl(postImageUrl)
                .user(user)
                .build();
    }
}
