package com.sini.mysns.api.controller.comment.dto;

import com.sini.mysns.domain.comment.Comment;
import lombok.Builder;

import java.util.List;

@Builder
public record CommentResponse(
        List<FindComment> Comments
) {
    public static CommentResponse from(List<Comment> commentList) {
        return new CommentResponse(
                commentList.stream()
                        .map(FindComment::from)
                        .toList()
        );
    }

    public record FindComment(
            String comment,
            Long postId,
            Long memberId,
            Long commentId,
            String memberName,
            String url
    ){
        public static FindComment from(Comment comment)
        {
            return new FindComment(
                    comment.getComment(),
                    comment.getPost().getPostId(),
                    comment.getMember().getMemberId(),
                    comment.getCommentId(),
                    comment.getMember().getMemberName(),
                    comment.getMember().getUrl()
            );
        }
    }
}

