package com.sini.mysns.domain.comment;

import com.sini.mysns.domain.member.Member;
import com.sini.mysns.domain.post.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@RequiredArgsConstructor
public class Comment  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Comment(Long commentId, String comment, Post post, Member member)
    {
        this.commentId = commentId;
        this.comment = comment;
        this.post = post;
        this.member = member;
    }

    public void update(Comment updateComment)
    {
        changeContent(updateComment.getComment());
    }

    public void changeContent(String comment)
    {
        if(comment == null || comment.isBlank())
        {
            return ;
        }
        this.comment = comment;
    }
}
