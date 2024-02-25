package com.sini.mysns.api.service.postImage.dto;

public record CreatePostImageRequest(
        String imageUrl,
        Integer imageOrder
) {
}
