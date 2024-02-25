package com.sini.mysns.api.service.comment.dto;

public record CreateCommentServiceRequest(
        String comment,
        Long postId,
        Long memberId
) {}
