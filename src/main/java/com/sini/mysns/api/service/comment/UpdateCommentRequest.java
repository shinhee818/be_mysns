package com.sini.mysns.api.service.comment;

import com.sini.mysns.api.service.comment.dto.UpdateCommentServiceRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateCommentRequest(
        @NotEmpty(message = "변경 할 댓글은 필수입니다")
        String comment,

        @NotNull(message = "memberId는 필수입니다")
        Long memberId
) {
    public UpdateCommentServiceRequest toServiceDto(Long commentId)
    {
            return new UpdateCommentServiceRequest(comment, commentId,memberId);
    }
}
