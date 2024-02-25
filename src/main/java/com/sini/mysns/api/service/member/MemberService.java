package com.sini.mysns.api.service.member;

import com.sini.mysns.api.auth.dto.AuthLoginSuccessRequest;
import com.sini.mysns.api.auth.dto.AuthLoginSuccessResponse;
import com.sini.mysns.api.controller.member.dto.FindMemberResponse;
import com.sini.mysns.api.service.member.dto.CreateMemberServiceRequest;
import com.sini.mysns.api.service.member.dto.UpdateMemberServiceRequest;
import com.sini.mysns.domain.member.Member;
import com.sini.mysns.domain.member.MemberRepository;
import com.sini.mysns.global.config.security.MemberDTO;
import com.sini.mysns.global.config.security.TokenProvider;
import com.sini.mysns.global.exception.ApiException;
import com.sini.mysns.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public Long createMember(CreateMemberServiceRequest request)
    {
        if (memberRepository.existsByEmail(request.email()))
        {
            throw new ApiException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        Member member = Member.builder()
                .memberName(request.memberName())
                .email(request.email())
                .age(request.age())
                .url(request.url())
                .build();

        return memberRepository.save(member).getMemberId();
    }

    @Transactional(readOnly = true)
    public FindMemberResponse findMember(Long memberId)
    {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new ApiException(ErrorCode.MEMBER_NOT_FOUND));
        return FindMemberResponse.from(member);
    }

    public Long updateMember(UpdateMemberServiceRequest request)
    {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Member updateMember = Member.builder()
                .memberName(request.memberName())
                .age(request.age())
                .build();

        member.update(updateMember);
        return member.getMemberId();
    }

    public void deleteMember(Long memberId)
    {
        Member member= memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));
        memberRepository.delete(member);
    }

    public AuthLoginSuccessResponse login(AuthLoginSuccessRequest request)
    {
        Member member = memberRepository.findByEmail(request.email()).orElseThrow();

        if(!member.matchPassword(request.password(), passwordEncoder))
        {
            throw new RuntimeException("Password 틀림");
        }

        MemberDTO dto = MemberDTO.toDTO(member);
        String accessToken = tokenProvider.generateAccessToken(dto);
        return new AuthLoginSuccessResponse(accessToken, member.getEmail(),member.getMemberId());
    }

    public Long join(CreateMemberServiceRequest request)
    {
        validateDuplicateMember(request);
        Member member = Member.join(request.email(), request.memberName(), request.age(), request.url(), request.password(), passwordEncoder);
        return memberRepository.save(member).getMemberId();
    }

    public void validateDuplicateMember(CreateMemberServiceRequest request)
    {
        if (!request.password().equals(request.confirmPassword()))
        {
            throw new IllegalArgumentException("확인 비밀번호가 다릅니다.");
        }

        if( memberRepository.existsByEmail(request.email()))
        {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

}
