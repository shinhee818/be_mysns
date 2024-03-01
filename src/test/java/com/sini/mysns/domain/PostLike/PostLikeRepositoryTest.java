package com.sini.mysns.domain.PostLike;

import com.sini.mysns.api.service.like.PostLikeService;
import com.sini.mysns.api.service.like.dto.PostLikeServiceRequest;
import com.sini.mysns.domain.PostCategory;
import com.sini.mysns.domain.member.Member;
import com.sini.mysns.domain.member.MemberRepository;
import com.sini.mysns.domain.post.Post;
import com.sini.mysns.domain.post.PostRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@SpringBootTest
class PostLikeRepositoryTest {

    @Autowired
    PostLikeRepository postLikeRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostLikeService postLikeService;

    @Autowired
    EntityManager entityManager;

    @Test
    void findByMember() {
        Member member = memberRepository.save(
                Member.builder()
                        .memberName("sini")
                        .email("sini@gmail.com")
                        .age(22)
                        .url("tlsgml@gmail.com")
                        .build()
        );
        Post post = postRepository.save(
                Post.builder()
                        .title("title")
                        .content("content")
                        .postCategory(PostCategory.BACKEND)
                        .member(member)
                        .build()
        );
        PostLikeServiceRequest request = new PostLikeServiceRequest(
                post.getPostId(),
                member.getMemberId()
        );

        postLikeService.like(request);
        List<PostLike> postLikes = postLikeRepository.findByMember(member);

        Assertions.assertThat(postLikes).hasSize(1);
        Assertions.assertThat(postLikes.stream()
                .map(postLike -> postLike.getPost().getContent())
                .collect(Collectors.toList())).containsExactly("content");
    }
}