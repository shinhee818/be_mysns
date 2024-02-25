package com.sini.mysns.api.controller.member.dto;

import com.sini.mysns.api.service.member.dto.CreateMemberServiceRequest;
import jakarta.validation.constraints.*;

public record MemberRequest(
        @NotEmpty(message = "유저 이름은 필수입니다")
        String memberName,

        @Email(message = "email 형식이 아닙니다")
        String email,

        @Min(message = "0보다 커야합니다", value = 1)
        @Max(message = "잘못된 값입니다", value = 130)
        int age,
        String imageUrl
) {
    public CreateMemberServiceRequest toServiceDto()
    {
        return new CreateMemberServiceRequest(memberName, email,age, imageUrl, toServiceDto().password(), toServiceDto().confirmPassword());
    }
}
