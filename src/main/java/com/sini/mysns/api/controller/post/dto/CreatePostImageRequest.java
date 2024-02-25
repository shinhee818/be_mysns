package com.sini.mysns.api.controller.post.dto;

import com.sini.mysns.domain.post.PostImage;

public record CreatePostImageRequest(String url,
                                     int postImageOrder
) {
    public PostImage toEntity() {
        return PostImage.builder()
                .url(url)
                .postImageOrder(postImageOrder)
                .build();
    }
}
