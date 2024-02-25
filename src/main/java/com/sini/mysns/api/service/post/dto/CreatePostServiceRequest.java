package com.sini.mysns.api.service.post.dto;

import com.sini.mysns.api.controller.post.dto.CreatePostImageRequest;
import com.sini.mysns.domain.PostCategory;

import java.util.List;

public record CreatePostServiceRequest (
        String title,
        String content,
        List<String> tagList,
        List<CreatePostImageRequest> postImages,
        PostCategory postCategory

){}
