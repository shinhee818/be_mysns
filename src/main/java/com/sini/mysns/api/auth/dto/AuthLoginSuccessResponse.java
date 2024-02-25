package com.sini.mysns.api.auth.dto;

public record AuthLoginSuccessResponse(
        String accessToken,
        String email,

        Long memberId
) {}
