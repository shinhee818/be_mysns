package com.sini.mysns.api.controller.comment.dto;

import com.sini.mysns.api.service.comment.dto.CreateCommentServiceRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateCommentRequest(
        @NotEmpty(message = "댓글은 필수입니다")
        String comment,

        @NotNull(message = "memberId는 필수입니다")
        Long memberId,

        @NotNull(message = "postId는 필수입니다")
        Long postId
) {
        public CreateCommentServiceRequest toServiceDto()
        {
                return new CreateCommentServiceRequest(comment,postId,memberId);
        }
}
