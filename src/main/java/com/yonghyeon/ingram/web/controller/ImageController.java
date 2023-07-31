package com.yonghyeon.ingram.web.controller;

import com.yonghyeon.ingram.config.auth.PrincipalDetails;
import com.yonghyeon.ingram.domain.image.Image;
import com.yonghyeon.ingram.handler.CustomValidationException;
import com.yonghyeon.ingram.service.BookmarkService;
import com.yonghyeon.ingram.service.ImageService;
import com.yonghyeon.ingram.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;
    private final BookmarkService bookmarkService;

    @GetMapping({"/", "/image/story"})
    public String story() {
        return "image/story";
    }

    @GetMapping({ "/image/popular"})
    public String popular(Model model) {

        // api는 데이터를 리턴하는 서버인데, 여기서는 데이터를 모델에 담아 들고가기만 하면 되므로 api로 하지 않음.(ajax 필요x)
        List<Image> images = imageService.popularImages();
        model.addAttribute("images",images);

        return "image/popular";
    }

    @GetMapping({ "/image/bookmark"})
    public String bookmark(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        List<Image> bookmarkImages = bookmarkService.bookmarkImages(principalDetails.getUser().getId());
        model.addAttribute("images",bookmarkImages);

        return "image/bookmark";
    }

    @GetMapping({ "/image/upload"})
    public String upload() {
        return "image/upload";
    }

    @PostMapping({"/image"})
    public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        // 얘는 공통처리가 안되기 때문에 그대로 둠
        if(imageUploadDto.getFile().isEmpty()) {
            throw new CustomValidationException("사진이 첨부되지 않았습니다.", null);
        }

        imageService.upload(imageUploadDto, principalDetails);

        return "redirect:/user/"+principalDetails.getUser().getId();
    }
}
