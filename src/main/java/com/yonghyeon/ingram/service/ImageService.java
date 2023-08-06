package com.yonghyeon.ingram.service;

import com.yonghyeon.ingram.config.auth.PrincipalDetails;
import com.yonghyeon.ingram.domain.image.Image;
import com.yonghyeon.ingram.domain.image.ImageRepository;
import com.yonghyeon.ingram.web.dto.image.ImageSearch;
import com.yonghyeon.ingram.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${file.path}")
    private String uploadFolder;

    public void upload(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {

        UUID uuid = UUID.randomUUID();

        String filename = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();

        Path imageFilePath = Paths.get(uploadFolder + filename);

        try {
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Image image = imageUploadDto.toEntity(filename, principalDetails.getUser());
        imageRepository.save(image);
    }

    @Transactional(readOnly = true)
    public List<Image> getImages(Long principalId, ImageSearch imageSearch) {

        List<Image> images = imageRepository.mGetImages(principalId, imageSearch);

        images.forEach((image)-> {

            image.setLikeCount(image.getLikes().size());

            image.getLikes().forEach((like)-> {

                // 로그인 회원이 해당 이미지에 '좋아요'를 누른 상태인지 판별
                if(like.getUser().getId() == principalId) {
                    image.setLikeState(true);
                }
            });
            image.getBookmarks().forEach((bookmark)-> {

                // 로그인 회원이 해당 이미지를 '북마크'한 상태인지 판별
                if(bookmark.getUser().getId() == principalId) {
                    image.setBookmarkState(true);
                }
            });
        });

        return images;
    }

    @Transactional(readOnly = true)
    public List<Image> popularImages() {

        return imageRepository.mPopularImages();
    }

}
