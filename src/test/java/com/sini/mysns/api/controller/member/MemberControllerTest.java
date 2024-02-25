package com.sini.mysns.api.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sini.mysns.api.controller.member.dto.MemberRequest;
import com.sini.mysns.api.service.member.MemberService;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@WebMvcTest(controllers = MemberController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTCheckFilter.class)
})
class MemberControllerTest {

    @MockBean
    MemberService memberService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("")
    @Test
    void createMemberInvalidMinAge() throws Exception
    {
        // given
        MemberRequest request = new MemberRequest(
                "sini",
                "abc@naver.com",
                -1,
                "tlsgml818@gmail.com"
        );
        String requestJson = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/member")
                    .content(requestJson)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    // then
                    .andExpect(jsonPath("$.errorMessage").value("0보다 커야합니다"))
                    .andExpect(jsonPath("$.code").value("Bad Request"))
                    .andExpect(jsonPath("$.status").value("400"));
    }

    @DisplayName("")
    @Test
    void createMemberInvalidMaxdAge() throws Exception
    {
        // given
        MemberRequest request = new MemberRequest(
                "sini",
                "abc@naver.com",
                131,
                "tlsgml818@gmail.com"
        );
        String requestJson = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/member")
                        .content(requestJson)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                // then
                .andExpect(jsonPath("$.errorMessage").value("잘못된 값입니다"))
                .andExpect(jsonPath("$.code").value("Bad Request"))
                .andExpect(jsonPath("$.status").value("400"));
    }

    @DisplayName("email 형식 예외")
    @Test
    void createMemberInvalidEmail() throws Exception {
        MemberRequest request = new MemberRequest(
                "sini",
                "abc.naver.com",
                13,
                "tlsgml818@gmail.com"
        );
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/member")
                        .content(requestJson)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())

                .andExpect(jsonPath("$.errorMessage").value("email 형식이 아닙니다"))
                .andExpect(jsonPath("$.code").value("Bad Request"))
                .andExpect(jsonPath("$.status").value("400"));
    }

    @DisplayName("삭제 예외")
    @Test
    void deleteMemberInvalid() throws Exception {
        MemberRequest request = new MemberRequest(
                "sini",
                "abc@naver.com",
                13,
                "tlsgml818@gmail.com"
        );
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.delete("/{memberId}", 13)
                        .content(requestJson)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.status").value("500"));
    }

}