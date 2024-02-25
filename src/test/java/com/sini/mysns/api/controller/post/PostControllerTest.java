package com.sini.mysns.api.controller.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.sini.mysns.api.controller.post.dto.CreatePostImageRequest;
import com.sini.mysns.api.controller.post.dto.CreatePostRequest;
import com.sini.mysns.api.controller.post.dto.UpdatePostRequest;
import com.sini.mysns.api.service.post.PostService;
import com.sini.mysns.domain.PostCategory;
import com.sini.mysns.global.config.security.filter.JWTCheckFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@WebMvcTest(controllers = PostController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTCheckFilter.class)})
class PostControllerTest {

    @MockBean
    PostService postService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("타이틀 필수 값 테스트")
    @Test
    void createPostInvalid() throws Exception
    {
        CreatePostRequest request = new CreatePostRequest(
            "",
                "content",
                List.of("tag1", "tag2"),
                Lists.newArrayList(
                        new CreatePostImageRequest("image1-imageUrl", 1),
                        new CreatePostImageRequest("image2-imageUrl", 2)
                ),
                PostCategory.BACKEND
        );

        String requestJson = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/post")
                        .content(requestJson)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                // then
                .andExpect(jsonPath("$.errorMessage").value("타이틀은 필수입니다"))
                .andExpect(jsonPath("$.code").value("Bad Request"))
                .andExpect(jsonPath("$.status").value("400"));
    }

    @DisplayName("컨텐트 필수 값 테스트")
    @Test
    void updatePostInvalid() throws Exception
    {
        UpdatePostRequest request = new UpdatePostRequest(
                "title",
                "",
                PostCategory.BACKEND
        );

        String requestJson = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(MockMvcRequestBuilders.put("/api/post/1")
                        .content(requestJson)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                // then
                .andExpect(jsonPath("$.errorMessage").value("컨텐트는 필수입니다"))
                .andExpect(jsonPath("$.code").value("Bad Request"))
                .andExpect(jsonPath("$.status").value("400"));
    }

    @DisplayName("포스트 삭제 테스트")
    @Test
    void deletePost() throws Exception
    {
        CreatePostRequest request = new CreatePostRequest(
                "title",
                "content",
                List.of("tag1", "tag2"),
                Lists.newArrayList(
                        new CreatePostImageRequest("image1-imageUrl", 1),
                        new CreatePostImageRequest("image2-imageUrl", 2)
                ),
                PostCategory.BACKEND
        );

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/post/1")
                        .content(requestJson)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }
}