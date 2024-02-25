package com.sini.mysns.domain.PostLike;

import com.sini.mysns.domain.member.Member;
import com.sini.mysns.domain.post.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(indexes = @Index(name = "post_like_idx", unique = true, columnList = "member_id, post_id"))
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postLikeId;

    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public PostLike(Post post, Member member)
    {
        this.post = post;
        this.member = member;
    }
}
