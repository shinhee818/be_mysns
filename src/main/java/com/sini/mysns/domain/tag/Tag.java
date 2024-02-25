package com.sini.mysns.domain.tag;


import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tag_id")
    private Long tagId;

    @Column(nullable = false, unique = true, name = "tag_content")
    private String tagContent;

    @Builder
    public Tag(Long tagId, String tagContent)
    {
        this.tagId = tagId;
        this.tagContent = tagContent;
    }
}
