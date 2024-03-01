package com.sini.mysns.global.config.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {

    public static String currentUserEmail()
    {
        MemberDTO memberDto = (MemberDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return memberDto.getUsername();
    }

    public static Long currentUserId()
    {
        MemberDTO memberDTO = (MemberDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Long.valueOf(memberDTO.getMemberId());
    }
}
