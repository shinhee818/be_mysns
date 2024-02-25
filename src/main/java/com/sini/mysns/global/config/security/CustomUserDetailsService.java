package com.sini.mysns.global.config.security;

import com.sini.mysns.domain.member.Member;
import com.sini.mysns.domain.member.MemberRepository;
import com.sini.mysns.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        log.info("[START] - loadUserByUsername");
        Member member = memberRepository.getWithRoles(username);

        if(member == null)
        {
            throw new UsernameNotFoundException(ErrorCode.MEMBER_NOT_FOUND.getMessage());
        }

        return new MemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.getMemberName(),
                member.getMemberId().toString(),
                member.getMemberRoles().stream()
                        .map(Enum::name)
                        .collect(Collectors.toList())
        );
    }
}