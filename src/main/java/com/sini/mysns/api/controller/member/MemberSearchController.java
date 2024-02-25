package com.sini.mysns.api.controller.member;

import com.sini.mysns.api.controller.member.dto.FindMemberResponse;
import com.sini.mysns.domain.member.Member;
import com.sini.mysns.domain.member.MemberRepository;
import com.sini.mysns.global.exception.ApiException;
import com.sini.mysns.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/member/search")
@RestController
public class MemberSearchController {

    private final MemberRepository memberRepository;

    @GetMapping("/{memberId}")
    public FindMemberResponse findMember(@PathVariable Long memberId)
    {
        Member member = memberRepository.findMember(memberId)
                .orElseThrow(()->new ApiException(ErrorCode.MEMBER_NOT_FOUND));
        return FindMemberResponse.from(member);
    }
}
