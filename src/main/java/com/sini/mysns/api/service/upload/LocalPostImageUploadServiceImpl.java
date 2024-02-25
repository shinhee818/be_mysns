package com.sini.mysns.api.service.upload;

import com.google.common.collect.Sets;
import com.sini.mysns.global.exception.ApiException;
import com.sini.mysns.global.exception.ErrorCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Log4j2
@Service
public class LocalPostImageUploadServiceImpl implements PostImageUploadService {

    @Value("${upload.image.post}")
    private String uploadImagePath;

    @Override
    public String upload(MultipartFile multipartFile) throws Exception
    {
        String orginalFilename = multipartFile.getOriginalFilename();

        if (orginalFilename == null || orginalFilename.isBlank())
        {
            throw new RuntimeException();
        }

        int index = orginalFilename.lastIndexOf(".");
        String fileNameWithoutExtension = orginalFilename.substring(0, index);
        String imgType = orginalFilename.substring(index+1).toLowerCase();

        if(!Sets.newHashSet("jpg", "jpeg", "png").contains(imgType))
        {
            throw new ApiException(ErrorCode.WRONG_IMAGE_FORMAT);
        }
        String savePath = UUID.randomUUID() + "_" + fileNameWithoutExtension +".jpg";
        File file = new File(uploadImagePath + savePath);
        multipartFile.transferTo(file);
        log.info("saved image imageUrl : {}", savePath);
        return savePath;
    }
}