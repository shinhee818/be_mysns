package com.sini.mysns.api.auth;

import com.sini.mysns.api.auth.dto.AuthLoginSuccessRequest;
import com.sini.mysns.api.auth.dto.AuthLoginSuccessResponse;
import com.sini.mysns.api.service.member.MemberService;
import com.sini.mysns.api.service.member.dto.CreateMemberServiceRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public Long join(@Valid @RequestBody CreateMemberServiceRequest request)
    {
        return memberService.join(request);
    }

    @PostMapping("/login")
    public AuthLoginSuccessResponse login(@Valid @RequestBody AuthLoginSuccessRequest request)
    {
        return memberService.login(request);
    }
}