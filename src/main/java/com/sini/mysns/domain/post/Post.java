package com.sini.mysns.domain.post;

import com.sini.mysns.domain.BaseTimeEntity;
import com.sini.mysns.domain.PostCategory;
import com.sini.mysns.domain.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@ToString(exclude = {"postTagList"})
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition="TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostTag> postTagList = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<PostImage> postImages = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private PostCategory postCategory;

    private int viewCount;

    @Builder
    public Post(
            Long postId,
            String title,
            String content,
            Member member,
            Set<PostTag> postTags,
            Set<PostImage> postImages,
            PostCategory postCategory
    ) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.member = member;
        this.postTagList = postTags;
        this.postImages = postImages;
        this.postCategory = postCategory;
    }

    public void update(Post updatePost)
    {
        setTitle(updatePost.getTitle());
        setContent(updatePost.getContent());
    }

    private void setContent(String content)
    {
        if(content == null || content.isBlank())
        {
            return ;
        }
        this.content = content;
    }

    private void setTitle(String title)
    {
        if(title== null || title.isBlank())
        {
            return ;
        }
        this.title = title;
    }

    public void addPostTag(PostTag postTag)
    {
        if (this.getPostTagList() == null)
        {
            this.postTagList = new HashSet<>();
        }

        this.postTagList.add(postTag);
        postTag.setPost(this);
    }

    public void addPostImage(PostImage postImage)
    {
        if (this.getPostImages() == null)
        {
            this.postImages = new HashSet<>();
        }

        this.getPostImages().add(postImage);
        postImage.setPost(this);
    }
}
