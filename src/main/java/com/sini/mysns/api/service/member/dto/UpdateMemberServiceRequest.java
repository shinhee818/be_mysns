package com.sini.mysns.api.service.member.dto;

import com.sini.mysns.domain.member.MemberRepository;

public record UpdateMemberServiceRequest (
         String memberName,
         Long memberId,
         int age
) {}