package com.sini.mysns.api.service.post;

import com.google.common.collect.Lists;
import com.sini.mysns.IntegrationTestSupporter;
import com.sini.mysns.api.controller.post.dto.CreatePostImageRequest;
import com.sini.mysns.api.service.post.dto.CreatePostServiceRequest;
import com.sini.mysns.api.service.post.dto.UpdatePostServiceRequest;
import com.sini.mysns.domain.member.Member;
import com.sini.mysns.domain.member.MemberRepository;
import com.sini.mysns.domain.post.Post;
import com.sini.mysns.domain.post.PostRepository;
import com.sini.mysns.domain.post.PostTag;
import com.sini.mysns.domain.tag.Tag;
import com.sini.mysns.domain.tag.TagRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.sini.mysns.domain.PostCategory.BACKEND;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class PostServiceTest extends IntegrationTestSupporter {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @DisplayName("포스트 생성 테스트")
    @Test
    void createPost()
    {
        //given
        tagRepository.saveAll(
                List.of(
                        Tag.builder().tagContent("sini1").build(),
                        Tag.builder().tagContent("sini2").build()
                )
        );

        entityManager.flush();
        entityManager.clear();

        CreatePostServiceRequest request = new CreatePostServiceRequest(
                "title",
                "content",
                Lists.newArrayList("sini1", "sini2", "sini3"),
                Lists.newArrayList(
                        new CreatePostImageRequest("image1-imageUrl", 1),
                        new CreatePostImageRequest("image2-imageUrl", 2)
                ),
                BACKEND
        );
        //when
        Long postId = postService.createPost(request);

        //then
        assertThat(postId).isNotNull();
        List<Post> posts = postRepository.findAll();

        Post post = posts.get(0);
        assertThat(post).extracting(Post::getTitle, Post::getContent, Post::getPostCategory)
                        .contains("title", "content",BACKEND);
        Set<PostTag> postTags = post.getPostTagList();
        assertThat(postTags).hasSize(3);
        assertThat(postTags).extracting(postTag -> postTag.getTag().getTagContent())
                .contains("sini1", "sini2", "sini3");
    }

    @DisplayName("포스트 수정 테스트")
    @Test
    void updatePost() {
        //given
        Member member = memberRepository.findByEmail("master@master.com").orElseThrow();

        Post post = postRepository.save(
                Post.builder()
                        .title("title")
                        .content("content")
                        .member(member)
                        .build()
        );
        UpdatePostServiceRequest request = new UpdatePostServiceRequest(
                "updatedTitle",
                "updatedContent",
                post.getPostId()
        );

        entityManager.flush();
        entityManager.clear();


        //when
        Long postId = postService.updatePost(request);

        //then
        AssertionsForClassTypes.assertThat(postId).isNotNull();
        List<Post> posts = postRepository.findAll();
        assertThat(posts).hasSize(1);
        AssertionsForClassTypes.assertThat(posts.get(0))
                .extracting(Post::getTitle, Post::getContent)
                .contains("updatedTitle","updatedContent");
    }

    @DisplayName("포스트 삭제 테스트")
    @Test
    void deletePost()
    {
        //given
        Member member = memberRepository.findByEmail("master@master.com").orElseThrow();
        Post post = postRepository.save(
                Post.builder()
                        .title("title")
                        .content("content")
                        .member(member)
                        .build()
        );
        entityManager.flush();
        entityManager.clear();

        //when
        postService.deletePost(post.getPostId());

        //then
        List<Post> posts = postRepository.findAll();
        assertThat(posts).isEmpty();
    }
}