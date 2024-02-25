package com.sini.mysns.domain.post;

import com.sini.mysns.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class PostImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postImageId;
    private String url;
    private int postImageOrder;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public PostImage(Long postImageId, String url, int postImageOrder) {
        this.postImageId = postImageId;
        this.url = url;
        this.postImageOrder = postImageOrder;
    }
}
