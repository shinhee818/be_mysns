package com.sini.mysns.api.controller.upload;

import com.sini.mysns.api.service.upload.PostImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/image/post")
public class PostImageUploadController {

    private final PostImageUploadService postImageUploadService;

    @Autowired
    public PostImageUploadController(PostImageUploadService postImageUploadService)
    {
        this.postImageUploadService = postImageUploadService;
    }

    @PostMapping
    public String insert(@RequestPart(value = "file") MultipartFile multipartFile) throws Exception
    {
        return postImageUploadService.upload(multipartFile);
    }
}
