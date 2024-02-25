package com.sini.mysns.api.controller.member.dto;

import com.sini.mysns.api.service.member.dto.UpdateMemberServiceRequest;

public record UpdateMemberRequest(
        String memberName,
        int age
) {
    public UpdateMemberServiceRequest toServiceDto(Long memberId)
    {
        return new UpdateMemberServiceRequest(memberName, memberId, age);
    }
}


