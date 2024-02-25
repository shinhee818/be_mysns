package com.sini.mysns.api.controller.postLike.dto;

import com.sini.mysns.api.service.like.dto.PostLikeServiceRequest;
import jakarta.validation.constraints.NotNull;

public record PostLikeRequest(
        @NotNull(message = "memberId는 필수입니다")
        Long memberId,

        @NotNull(message = "postId는 필수입니다")
        Long postId
)
{
    public PostLikeServiceRequest toServiceDto()
    {
        return new PostLikeServiceRequest(postId, memberId);
    }
}
