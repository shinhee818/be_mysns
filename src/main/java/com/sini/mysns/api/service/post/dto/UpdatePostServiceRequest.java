package com.sini.mysns.api.service.post.dto;

public record UpdatePostServiceRequest(
        String title,
        String content,
        Long postId
) {}
