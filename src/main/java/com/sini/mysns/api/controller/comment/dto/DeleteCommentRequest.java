package com.sini.mysns.api.controller.comment.dto;

import com.sini.mysns.api.service.comment.dto.DeleteCommentServiceRequest;
import jakarta.validation.constraints.NotNull;

public record DeleteCommentRequest(
        @NotNull(message = "postId는 필수입니다")
        Long postId,

        @NotNull(message = "memberId는 필수입니다")
        Long memberId,

        @NotNull(message = "commentId은 필수입니다")
        Long commentId
) {
    public DeleteCommentServiceRequest toServiceDto(Long commentId)
    {
        return new DeleteCommentServiceRequest(postId,memberId,commentId);
    }
}
