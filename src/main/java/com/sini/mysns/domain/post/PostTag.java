package com.sini.mysns.domain.post;

import com.sini.mysns.domain.tag.Tag;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@ToString(exclude = {"post", "tag"})
@Table(indexes = @Index(name = "post_tag_idx",columnList = "post_id, tag_id", unique = true))
public class PostTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tag_id")
    private Tag tag;

    public void setPost(Post post)
    {
        this.post = post;
    }

    @Builder
    public PostTag(Long postTagId, Post post, Tag tag)
    {
        this.postTagId = postTagId;
        this.post = post;
        this.tag = tag;
    }
}
