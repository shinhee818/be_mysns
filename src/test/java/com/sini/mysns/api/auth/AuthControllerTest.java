package com.sini.mysns.api.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sini.mysns.api.auth.dto.AuthLoginSuccessRequest;
import com.sini.mysns.api.service.member.MemberService;
import com.sini.mysns.api.service.member.dto.CreateMemberServiceRequest;
import com.sini.mysns.global.config.security.filter.JWTCheckFilter;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@WebMvcTest(controllers = AuthController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTCheckFilter.class)
})
class AuthControllerTest {
    @MockBean
    MemberService memberService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void join() throws Exception{
        CreateMemberServiceRequest request = new CreateMemberServiceRequest(
                "name",
                "name@name.com",
                24,
                "imageUrl",
                "1234",
                "1234"
        );

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                .content(requestJson)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}