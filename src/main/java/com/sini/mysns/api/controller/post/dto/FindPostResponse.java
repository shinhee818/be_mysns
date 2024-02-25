package com.sini.mysns.api.controller.post.dto;

import com.sini.mysns.domain.post.Post;
import com.sini.mysns.domain.post.PostImage;
import com.sini.mysns.domain.post.PostTag;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record FindPostResponse(
        Long postId,
        String title,
        String content,
        LocalDateTime regsterDate,
        Long memberId,
        String memberName,
        List<String> tagList,
        List<String> images
) {
    public static FindPostResponse from(Post post)
    {
        Set<PostTag> postTagList = post.getPostTagList();

        List<String> tagContentList = postTagList.stream()
                .map(postTag -> postTag.getTag().getTagContent())
                .collect(Collectors.toList());

        List<String> images =  post.getPostImages().stream()
                .sorted(Comparator.comparing(PostImage::getPostImageOrder))
                .map(PostImage::getUrl)
                .collect(Collectors.toList());

        return new FindPostResponse(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getRegisterDate(),
                post.getMember().getMemberId(),
                post.getMember().getMemberName(),
                tagContentList,
                images
        );
    }
}
