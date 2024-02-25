package com.sini.mysns.api.controller.member.dto;

import com.sini.mysns.domain.member.Member;
// controller -> service -> domain
public record FindMemberResponse(
        Long memberId,
        String memberName,
        String email,
        int age,
        String url
) {
    public static FindMemberResponse from(Member member)
    {
        return new FindMemberResponse(
                member.getMemberId(),
                member.getMemberName(),
                member.getEmail(),
                member.getAge(),
                member.getUrl()
        );
    }
}

