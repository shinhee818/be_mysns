package com.sini.mysns.api.service.member;

import com.sini.mysns.domain.member.Member;

public class MemberTestHelper {

    public static Member MASTER = Member.builder()
            .memberName("master")
            .email("sini@m.com")
            .age(10)
            .url("imageUrl.com")
            .password("123")
            .build();
}
