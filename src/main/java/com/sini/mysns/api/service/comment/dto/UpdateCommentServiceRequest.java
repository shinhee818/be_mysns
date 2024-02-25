package com.sini.mysns.api.service.comment.dto;

public record UpdateCommentServiceRequest(
        String comment,
        Long commentId,
        Long memberId
) {}
