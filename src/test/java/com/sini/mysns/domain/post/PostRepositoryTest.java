package com.sini.mysns.domain.post;

import com.google.common.collect.Sets;
import com.sini.mysns.domain.PostCategory;
import com.sini.mysns.domain.member.Member;
import com.sini.mysns.domain.member.MemberRepository;
import com.sini.mysns.domain.tag.Tag;
import com.sini.mysns.domain.tag.TagRepository;
import com.sini.mysns.repository.PostQuerydslRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostQuerydslRepository postQuerydslRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void findPost()
    {
        // given
        Member member = memberRepository.save(Member.builder()
                .memberName("ag")
                .age(14)
                .email("ws@ne.com")
                .build());
        Tag tag1 = tagRepository.save(Tag.builder()
                .tagContent("tag1")
                .build());
        Tag tag2 = tagRepository.save(Tag.builder()
                .tagContent("tag2")
                .build());
        tagRepository.saveAll(List.of(tag1, tag2));
        PostTag postTag1 = PostTag.builder()
                .tag(tag1)
                .build();

        PostTag postTag2 = PostTag.builder()
                .tag(tag2)
                .build();
        Post post = postRepository.save(Post.builder()
                .title("title")
                .content("content")
                .postTags(
                        Sets.newHashSet(PostTag.builder()
                        .build())
                )
                .postCategory(PostCategory.BACKEND)
                .member(member)

                .build());

        post.addPostTag(postTag1);
        post.addPostTag(postTag2);
        entityManager.flush();
        entityManager.clear();

        // when
        Post findPost = postRepository.findOnePostById(post.getPostId()).orElseThrow();

        // then
        assertThat(post.getContent()).isEqualTo("content");
        assertThat(findPost.getPostTagList()).hasSize(2);

    }

}

