package com.sini.mysns.api.controller.postLike.dto;

import com.sini.mysns.domain.PostCategory;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PostLikeCount {
    private Long postId;
    private Long likeCount;
    private String content;
    private String title;
    private Long memberId;
    private String memberName;
    private String url;
    private LocalDateTime postRegisterDate;
    private PostCategory postCategory;

    @Setter
    private List<PostImageDto> postImagesList = new ArrayList<>();


    public PostLikeCount(
            Long postId,
            Long likeCount,
            String content,
            String title,
            Long memberId,
            String memberName,
            String url,
            LocalDateTime postRegisterDate,
            PostCategory postCategory
    ) {
        System.out.println("SINI CALL ~  ~ ~ ~");
        this.postId = postId;
        this.likeCount = likeCount;
        this.content = content;
        this.title = title;
        this.memberId = memberId;
        this.memberName = memberName;
        this.url = url;
        this.postRegisterDate = postRegisterDate;
        this.postCategory = postCategory;
    }
}