package com.sini.mysns.api.service.upload;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Primary
@Service
public class S3PostImageUploadServiceImpl implements PostImageUploadService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String upload(MultipartFile multipartFile)
    {
        try {
            String key = UUID.randomUUID().toString();
            String fileName = multipartFile.getOriginalFilename();
            String url = key;
            ObjectMetadata metadata= new ObjectMetadata();
            metadata.setContentType(multipartFile.getContentType());
            metadata.setContentLength(multipartFile.getSize());
            PutObjectResult putObjectResult = amazonS3Client.putObject(bucket, UUID.randomUUID().toString(), multipartFile.getInputStream(), metadata);
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}