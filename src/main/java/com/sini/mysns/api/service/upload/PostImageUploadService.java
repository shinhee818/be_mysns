package com.sini.mysns.api.service.upload;

import org.springframework.web.multipart.MultipartFile;

public interface PostImageUploadService {
    String upload(MultipartFile multipartFile) throws Exception;
}
