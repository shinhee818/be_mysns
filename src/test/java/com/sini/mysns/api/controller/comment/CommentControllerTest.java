package com.sini.mysns.api.controller.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.sini.mysns.api.controller.comment.dto.CreateCommentRequest;
import com.sini.mysns.api.controller.comment.dto.DeleteCommentRequest;
import com.sini.mysns.api.service.comment.CommentService;
import com.sini.mysns.api.service.comment.UpdateCommentRequest;
import com.sini.mysns.domain.PostCategory;
import com.sini.mysns.domain.comment.Comment;
import com.sini.mysns.domain.comment.CommentRepository;
import com.sini.mysns.domain.member.Member;
import com.sini.mysns.domain.post.Post;
import com.sini.mysns.domain.post.PostImage;
import com.sini.mysns.domain.post.PostTag;
import com.sini.mysns.domain.tag.Tag;
import com.sini.mysns.global.config.security.filter.JWTCheckFilter;
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
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@WebMvcTest(controllers = CommentController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTCheckFilter.class)}
)
public class CommentControllerTest {

    @MockBean
    CommentService commentService;

    @MockBean
    CommentRepository commentRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("댓글 필수 값 테스트")
    @Test
    void createComment() throws Exception
    {
        CreateCommentRequest request = new CreateCommentRequest(
                "",
                1L,
                2L
        );

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/comment")
                        .content(requestJson)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                //then
                .andExpect(jsonPath("$.errorMessage").value("댓글은 필수입니다"))
                .andExpect(jsonPath("$.code").value("Bad Request"))
                .andExpect(jsonPath("$.status").value("400"));
    }

    @DisplayName("댓글 수정 테스트")
    @Test
    void updateComment() throws Exception
    {
        UpdateCommentRequest request = new UpdateCommentRequest(
                "comment3",
                1L
        );

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/comment/1")
                        .content(requestJson)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        //then
    }





    @DisplayName("댓글 삭제 테스트")
    @Test
    void deleteComment() throws Exception
    {
        DeleteCommentRequest request = new DeleteCommentRequest(1L, 2L,3L);

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/comment/{commentId}/{memberId}",request.commentId(),request.memberId())
                        .content(requestJson)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("포스트 댓글 테스트")
    @Test
    void getComments() throws Exception
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
        Member member = Member.builder()
                .age(14)
                .email("s@n.com")
                .memberName("m")
                .memberId(1L)
                .build();

        Member member2 = Member.builder()
                .age(17)
                .email("s2@n.com")
                .memberName("m2")
                .memberId(2L)
                .build();


        Post post = Post.builder()
                .postId(1L)
                .title("title")
                .content("content")
                .member(member)
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
                .member(member2)
                .postCategory(PostCategory.FRONTEND)
                .postTags(postTags2)
                .postImages(postImages)
                .build();

        postTags2.forEach(postTag -> postTag.setPost(post2));

        BDDMockito.given(commentRepository.findCommentsByPostId(Mockito.any())).willReturn(List.of(
                Comment.builder().commentId(1L).comment("comment").member(member).post(post).build(),
                Comment.builder().commentId(2L).comment("comment2").member(member2).post(post).build()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/comment/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Comments").isArray())
                .andExpect(jsonPath("$.Comments.length()").value(2))
                .andExpect(jsonPath("$.Comments[0].comment").value("comment"));
    }
}
