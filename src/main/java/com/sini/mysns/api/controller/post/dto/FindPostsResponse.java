package com.sini.mysns.api.controller.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sini.mysns.domain.PostCategory;
import com.sini.mysns.domain.post.Post;
import com.sini.mysns.domain.post.PostImage;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Builder
public record FindPostsResponse(
        List<FindPost> findPosts,
        int totalPages,
        long totalElements
) {
    public static FindPostsResponse of(Page<Post> posts)
    {
        List<FindPost> findPosts = posts.stream()
                .map(FindPost::from)
                .collect(Collectors.toList());

        return FindPostsResponse.builder()
                .findPosts(findPosts)
                .totalElements(posts.getTotalElements())
                .totalPages(posts.getTotalPages())
                .build();
    }

    public record FindPost(
            Long postId,
            int viewCount,
            String title,
            String content,
            Long memberId,
            String memberName,
            String url,
            @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
            LocalDateTime postRegisterDate,
            PostCategory postCategory,
            String imageUrl
    ) {
        public static FindPost from(Post post)
        {
            return new FindPost(
                    post.getPostId(),
                    post.getViewCount(),
                    post.getTitle(),
                    post.getContent(),
                    post.getMember().getMemberId(),
                    post.getMember().getMemberName(),
                    post.getMember().getUrl(),
                    post.getRegisterDate(),
                    post.getPostCategory(),
                    post.getPostImages().stream()
                         .filter(s->s.getPostImageOrder() == 1)
                         .findAny()
                         .map(PostImage::getUrl)
                         .orElse("")
            );
        }
    }
}
