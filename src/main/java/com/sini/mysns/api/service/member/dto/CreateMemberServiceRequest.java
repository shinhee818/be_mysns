package com.sini.mysns.api.service.member.dto;

public record CreateMemberServiceRequest(
        String memberName,
        String email,
        int age,
        String url,
        String password,
        String confirmPassword
) {}
