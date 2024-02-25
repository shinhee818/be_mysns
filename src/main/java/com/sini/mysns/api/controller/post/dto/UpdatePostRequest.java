package com.sini.mysns.api.controller.post.dto;

import com.sini.mysns.domain.PostCategory;
import com.sini.mysns.api.service.post.dto.UpdatePostServiceRequest;
import jakarta.validation.constraints.NotEmpty;

public record UpdatePostRequest(
        @NotEmpty(message = "타이틀은 필수입니다")
        String title,
        @NotEmpty(message = "컨텐트는 필수입니다")
        String content,
        PostCategory postCategory
) {
    public UpdatePostServiceRequest toServiceDto(Long postId)
    {
        return new UpdatePostServiceRequest(title,content,postId);
    }
}
