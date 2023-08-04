package com.sparta.newpost.s3.service;

import com.sparta.newpost.s3.S3Uploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class S3Service {

    @Autowired
    private S3Uploader s3Uploader;

    @Transactional
        public void keepDiary(MultipartFile image) throws IOException {
        System.out.println("image save");
        if(!image.isEmpty()) {
            String storedFileName = s3Uploader.upload(image);
        }
    }
}
