package com.sini.mysns.api.controller.post.dto;

import com.sini.mysns.domain.PostCategory;
import com.sini.mysns.api.service.post.dto.CreatePostServiceRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreatePostRequest(
        @NotEmpty(message = "타이틀은 필수입니다")
        String title,

        @NotEmpty(message = "컨텐트는 필수입니다")
        String content,

        List<String> tagList,
        List<CreatePostImageRequest> postImages,
        PostCategory postCategory
) {
    public CreatePostServiceRequest toServiceDto()
    {
        return new CreatePostServiceRequest(title, content, tagList, postImages, postCategory);
    }
}
