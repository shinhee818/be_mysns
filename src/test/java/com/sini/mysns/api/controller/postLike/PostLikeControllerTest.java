package com.sini.mysns.api.controller.postLike;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sini.mysns.ControllerTestSupporter;
import com.sini.mysns.api.controller.postLike.dto.PostLikeRequest;
import com.sini.mysns.api.service.like.PostLikeService;
import com.sini.mysns.domain.PostLike.PostLikeRepository;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "master")
@WebMvcTest(controllers = PostLikeController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTCheckFilter.class)}
)
class PostLikeControllerTest extends ControllerTestSupporter {

    @MockBean
    PostLikeService postLikeService;

    @MockBean
    PostLikeRepository postLikeRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("좋아요 수 테스트")
    void countPostLikes() throws Exception
    {
        //given
        PostLikeRequest postLikeRequest = new PostLikeRequest(
                1L,
                2L
        );

        //when
        String requestJson = objectMapper.writeValueAsString(postLikeRequest);

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/like")
                .content(requestJson)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("특정 포스트 좋아요 개수 테스트")
    void findPostLike() throws Exception
    {
        //when
        BDDMockito.given(postLikeService.findLikedPostIds(Mockito.any())).willReturn(List.of(1L,2L));

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/like")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0]").value(1))
                .andExpect(jsonPath("$[1]").value(2));
    }
}