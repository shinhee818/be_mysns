package com.sini.mysns.api.service.posttag.dto;

public record CreatePostTagServiceRequest(
        String content,
        Long postId,
        Long tagId
) {}
