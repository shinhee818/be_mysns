package com.sini.mysns.api.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sini.mysns.domain.member.Member;
import com.sini.mysns.domain.member.MemberRepository;
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

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WithMockUser
@WebMvcTest(controllers = MemberSearchController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTCheckFilter.class)}
)
public class MemberSearchControllerTest {

    @MockBean
    MemberRepository memberRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("해당 멤버 찾기 테스트")
    void findMember() throws Exception
    {
        BDDMockito.given(
                memberRepository.findMember(Mockito.any()))
                    .willReturn(Optional.of(
                            Member.builder()
                                    .memberName("name")
                                    .memberId(1L)
                                    .email("s@sini.com")
                                    .age(14)
                                    .build()
                    ));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/member/search/1")
                        .with(csrf())
                        .header("Authorization", "token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                // then
                .andExpect(jsonPath("$.memberId").value("1"))
                .andExpect(jsonPath("$.memberName").value("name"))
                .andExpect(jsonPath("$.email").value("s@sini.com"))
                .andExpect(jsonPath("$.age").value("14"));
    }
}