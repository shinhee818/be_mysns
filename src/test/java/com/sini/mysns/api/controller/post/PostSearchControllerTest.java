package com.sini.mysns.api.controller.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.sini.mysns.ControllerTestSupporter;
import com.sini.mysns.api.controller.post.dto.FindPostsResponse;
import com.sini.mysns.domain.PostCategory;
import com.sini.mysns.domain.member.Member;
import com.sini.mysns.domain.post.Post;
import com.sini.mysns.domain.post.PostImage;
import com.sini.mysns.domain.post.PostRepository;
import com.sini.mysns.domain.post.PostTag;
import com.sini.mysns.domain.tag.Tag;
import com.sini.mysns.global.config.security.filter.JWTCheckFilter;
import com.sini.mysns.repository.PostQuerydslRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WithMockUser
@WebMvcTest(controllers = PostSearchController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTCheckFilter.class)}
)
class PostSearchControllerTest extends ControllerTestSupporter {

    @MockBean
    PostRepository postRepository;

    @MockBean
    PostQuerydslRepository postQuerydslRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("특정 포스트 찾기 테스트")
    void findPost() throws Exception
    {
        Set<PostTag> postTags = Sets.newHashSet(
                PostTag.builder()
                        .tag(Tag.builder().tagContent("tag1").build())
                        .build(),
                PostTag.builder()
                        .tag(Tag.builder().tagContent("tag2").build())
                        .build()
        );

        Set<PostImage> postImages = Sets.newHashSet(
                PostImage.builder()
                            .url("url1")
                            .postImageOrder(1)
                            .build(),
                PostImage.builder()
                        .url("url2")
                        .postImageOrder(2)
                        .build()
        );

        Post post = Post.builder()
                .postId(1L)
                .title("title")
                .content("content")
                .member(Member.builder()
                        .age(14)
                        .email("s@n.com")
                        .memberName("m")
                        .memberId(1L)
                        .build())
                .postCategory(PostCategory.BACKEND)
                .postTags(postTags)
                .postImages(postImages)
                .build();

        postTags.forEach(postTag -> postTag.setPost(post));

        BDDMockito.given(postQuerydslRepository.findFetchPostById(Mockito.any()))
                .willReturn(Optional.of(post));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/post/search/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value("1"))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.content").value("content"))
                .andExpect(jsonPath("$.memberName").value("m"))
                .andExpect(jsonPath("$.memberId").value("1"))
                .andExpect(jsonPath("$.tagList.length()").value("2"))
                ;
    }

    @Test
    @DisplayName("메인 포스트 테스트")
    void findPosts() throws Exception
    {
        Set<PostTag> postTags = Sets.newHashSet(
                PostTag.builder()
                        .tag(Tag.builder().tagContent("tag1").build())
                        .postTagId(1L)
                        .build(),
                PostTag.builder()
                        .tag(Tag.builder().tagContent("tag2").build())
                        .postTagId(2L)
                        .build()
        );
        Set<PostImage> postImages = Sets.newHashSet(
                PostImage.builder()
                        .url("url1")
                        .postImageOrder(1)
                        .build(),
                PostImage.builder()
                        .url("url2")
                        .postImageOrder(2)
                        .build()
        );


        Post post = Post.builder()
                .postId(1L)
                .title("title")
                .content("content")
                .member(Member.builder()
                        .age(14)
                        .email("s@n.com")
                        .memberName("m")
                        .memberId(1L)
                        .build())
                .postCategory(PostCategory.BACKEND)
                .postTags(postTags)
                .postImages(postImages)
                .build();

        postTags.forEach(postTag -> postTag.setPost(post));

        Set<PostTag> postTags2 = Sets.newHashSet(
                PostTag.builder()
                        .postTagId(3L)
                        .tag(Tag.builder().tagContent("tag1").build())
                        .build(),
                PostTag.builder()
                        .postTagId(4L)
                        .tag(Tag.builder().tagContent("tag2").build())
                        .build()
        );

        Post post2 = Post.builder()
                .postId(2L)
                .title("title2")
                .content("content2")
                .member(Member.builder()
                        .age(24)
                        .email("s@naver.com")
                        .memberName("k")
                        .memberId(2L)
                        .build())
                .postCategory(PostCategory.FRONTEND)
                .postTags(postTags2)
                .postImages(postImages)
                .build();

        postTags2.forEach(postTag -> postTag.setPost(post2));

        // given
        FindPostsResponse findPostsResponse = FindPostsResponse.builder()
                .findPosts(List.of(
                        FindPostsResponse.FindPost.from(post),
                        FindPostsResponse.FindPost.from(post2)
                ))
                .totalElements(10)
                .totalPages(12)
                .build();

        BDDMockito.given(postQuerydslRepository.findPosts(Mockito.any())).willReturn(findPostsResponse);

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/post/search")
                        .param("size", "2")
                        .param("page","0")
                        .param("sort","VIEWS")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.findPosts[0].title").value("title"))
                .andExpect(jsonPath("$.findPosts[0].content").value("content"))
                .andExpect(jsonPath("$.findPosts[0].postId").value("1"))
                .andExpect(jsonPath("$.findPosts[0].memberName").value("m"))
                .andExpect(jsonPath("$.findPosts[0].memberId").value("1"))

                .andExpect(jsonPath("$.findPosts[1].title").value("title2"))
                .andExpect(jsonPath("$.findPosts[1].content").value("content2"))
                .andExpect(jsonPath("$.findPosts[1].postId").value("2"))
                .andExpect(jsonPath("$.findPosts[1].memberName").value("k"))
                .andExpect(jsonPath("$.findPosts[1].memberId").value("2"));
    }
}