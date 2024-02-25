package com.sini.mysns.api.controller.postLike.dto;

import com.sini.mysns.domain.post.PostImage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PostImageDto {
    private int order;
    private String url;

    public static List<PostImageDto> of(List<PostImage> postImages)
    {
        return postImages.stream()
                .map(s-> new PostImageDto(s.getPostImageOrder(), s.getUrl()))
                .collect(Collectors.toList());
    }

    public PostImageDto(int order, String url) {
        this.order = order;
        this.url = url;
    }
}
