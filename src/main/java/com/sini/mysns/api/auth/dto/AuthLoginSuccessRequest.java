package com.sini.mysns.api.auth.dto;

import jakarta.validation.constraints.NotNull;

public record AuthLoginSuccessRequest(
        @NotNull(message = "필수 이메일")
        String email,
        String password
) {}
