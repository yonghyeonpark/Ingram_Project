package com.yonghyeon.ingram.service;

import com.yonghyeon.ingram.config.auth.PrincipalDetails;
import com.yonghyeon.ingram.domain.image.Image;
import com.yonghyeon.ingram.domain.image.ImageRepository;
import com.yonghyeon.ingram.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    // org.springframework
    @Value("${file.path}") // file.경로명
    private String uploadFolder;
    // private String uploadFolder = C:/Users/박용현/Desktop/ingram/upload/; 으로 해도 됨 but 여러 곳에 적용할 때 실수의 여지가 있음

    public void upload(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {

        // 동일한 파일명이 이미 들어와 있는 파일을 덮는 것을 방지하기위해 UUID 사용(유일성 보장)
        UUID uuid = UUID.randomUUID();
        // 극악의 확률로 UUID가 같을 것을 대비해 파일명과 조합
        String filename = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();// 실제 파일명

        Path imageFilePath = Paths.get(uploadFolder + filename);

        // 통신(외부에서 data 받아옴), I/O(하드디스크에 write, read) => 예외 발생 가능(읽으려는 파일이 존재하지 않을 때) / 컴파일에러가 아닌 런타임에러 => 따라서 예외를 잡아줘야 함
        try {
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes()); // 파일경로, 실제파일
        } catch (Exception e) {
            e.printStackTrace();
        }

        Image image = imageUploadDto.toEntity(filename, principalDetails.getUser());
        imageRepository.save(image);

        /*System.out.println("===============");
        System.out.println(image); // 실제로는 image.toString()이 호출
        System.out.println("===============");*/
    }

}
