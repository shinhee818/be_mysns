package com.sini.mysns.api.service.comment.dto;

public record DeleteCommentServiceRequest(
        Long postId,
        Long memberId,
        Long commentId
) {
}
