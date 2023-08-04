package com.sparta.newpost.s3.controller;
import com.sparta.newpost.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class S3Controller {
    private final S3Service s3Service;

    @ResponseBody
    @PostMapping("/S3")
    public void saveDiary(@RequestParam(value="image") MultipartFile image) throws IOException {
        System.out.println("DiaryController.saveDiary");
        System.out.println(image);
        s3Service.keepDiary(image);
    }
}
