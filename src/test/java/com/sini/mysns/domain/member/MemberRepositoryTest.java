package com.sini.mysns.domain.member;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;


    @Test
    void findByEmailTest() {
        //given
        Member member = memberRepository.save(Member.builder()
                .memberName("ag")
                .age(14)
                .email("ws@ne.com")
                .build());

        //when
        Member findMember = memberRepository.findByEmail(member.getEmail()).orElseThrow();

        //then
        assertThat(findMember.getMemberName()).isEqualTo("ag");
        assertThat(findMember.getEmail()).isEqualTo("ws@ne.com");



    }

    @Test
    void findMember() {
        //given
        Member member = memberRepository.save(Member.builder()
                .memberName("ag")
                .age(14)
                .email("ws@ne.com")
                .build());

        //when
        Member findMember = memberRepository.findMember(member.getMemberId()).orElseThrow();

        //then
        assertThat(findMember.getMemberName()).isEqualTo("ag");
        assertThat(findMember.getEmail()).isEqualTo("ws@ne.com");
        assertThat(findMember.getAge()).isEqualTo(14);
    }

    @Test
    void getWithRoles() {
        //given
        Member member = memberRepository.save(Member.builder()
                .memberName("ag")
                .age(14)
                .email("ws@ne.com")
                .build());

        //when
        Member findMember = memberRepository.getWithRoles(member.getEmail());

        //then
        assertThat(findMember.getMemberName()).isEqualTo("ag");
        assertThat(findMember.getEmail()).isEqualTo("ws@ne.com");
        assertThat(findMember.getAge()).isEqualTo(14);
    }
}