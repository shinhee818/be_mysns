package com.sini.mysns.api.service.like;

import com.sini.mysns.IntegrationTestSupporter;
import com.sini.mysns.api.service.like.dto.PostLikeServiceRequest;
import com.sini.mysns.domain.PostCategory;
import com.sini.mysns.domain.postlike.PostLike;
import com.sini.mysns.domain.postlike.PostLikeRepository;
import com.sini.mysns.domain.member.Member;
import com.sini.mysns.domain.member.MemberRepository;
import com.sini.mysns.domain.post.Post;
import com.sini.mysns.domain.post.PostRepository;
import com.sini.mysns.global.config.security.AuthUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class PostLikeServiceTest extends IntegrationTestSupporter {
    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostLikeRepository PostLikeRepository;

    @Autowired
    PostLikeService postLikeService;

    @Test
    @DisplayName("좋아요 테스트")
    void like() {
        //given
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail()).orElseThrow();
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

        //then
        postLikeService.like(request);

        //when
        List<PostLike> all = PostLikeRepository.findAll();

        assertThat(all).hasSize(1);
        assertThat(all).isNotNull();

    }
}