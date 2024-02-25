package com.sini.mysns.api.service.member;

import com.sini.mysns.api.controller.member.dto.FindMemberResponse;
import com.sini.mysns.domain.member.Member;
import com.sini.mysns.domain.member.MemberRepository;
import com.sini.mysns.api.service.member.dto.CreateMemberServiceRequest;
import com.sini.mysns.api.service.member.dto.UpdateMemberServiceRequest;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sini.mysns.api.service.member.MemberTestHelper.MASTER;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("멤버 서비스 테스트")
@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @DisplayName("멤버 생성")
    @Test
    @Transactional
    void createMember()
    {
        // given
        CreateMemberServiceRequest request = new CreateMemberServiceRequest(
                MASTER.getMemberName(),
                MASTER.getEmail(),
                MASTER.getAge(),
                MASTER.getUrl(),
                MASTER.getPassword(),
                MASTER.getPassword()
        );

        // when
        Long memberId = memberService.createMember(request);

        // then
        assertThat(memberId).isNotNull();
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(2);
        assertThat(members.get(0).getMemberName()).isEqualTo("sini");
        assertThat(members.get(0).getEmail()).isEqualTo("sini@sini.com");
    }

    @DisplayName("이메일은 중복될 수 없다.")
    @Test
    void uniqueEmail()
    {
        // given
        String duplicateEmail = MASTER.getEmail();
        memberRepository.save(MASTER);

        entityManager.flush();
        entityManager.clear();
        CreateMemberServiceRequest request = new CreateMemberServiceRequest(
                "newMember",
                MASTER.getEmail(),
                15,
                "tlsgml@gmail.com",
                "12234",
                "1234"
        );

        // when
        Assertions.assertThatThrownBy(()-> memberService.createMember(request))
                // then
                .isInstanceOf(RuntimeException.class)
                .hasMessage("이미 존재하는 이메일");
    }
    //멤버조회

    @DisplayName("멤버 수정")
    @Test
    void memberUpdate()
    {
        // given
        Member member = memberRepository.save(
                Member.builder()
                        .memberName("master")
                        .email("sini@naver.com")
                        .age(10)
                        .url("tlsgml@gmail.com")
                        .password("12345")
                        .build()
        );

        entityManager.flush();
        entityManager.clear();

        UpdateMemberServiceRequest request = new UpdateMemberServiceRequest(
                "updated",
                member.getMemberId(),
                22
        );

        // when
        Long memberId = memberService.updateMember(request);

        // then
        assertThat(memberId).isNotNull();
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(2);
        assertThat(members.get(1))
                .extracting(Member::getMemberName, Member::getAge)
                .contains("updated", 22);
    }

    @DisplayName("멤버 삭제")
    @Test
    void memberDelete()
    {
        //given
        Member member = memberRepository.save(
                Member.builder()
                        .memberName("master")
                        .email("sini@naver.com")
                        .age(10)
                        .url("tlsgml@gmail.com")
                        .build()
        );

        entityManager.flush();
        entityManager.clear();

        //when
        memberService.deleteMember(member.getMemberId());

        //then
        List<Member> members = memberRepository.findAll();
        Assertions.assertThat(members).hasSize(1);
    }

    @Test
    void findMember()
    {
        //given
        Member member = memberRepository.save(
                Member.builder()
                        .memberName("master")
                        .email("sini@naver.com")
                        .age(10)
                        .url("tlsgml@gmail.com")
                        .build()
        );
        entityManager.flush();
        entityManager.clear();

        //when
        FindMemberResponse response = memberService.findMember(member.getMemberId());

        //then
        assertThat(response).isNotNull();
        assertThat(response.memberId()).isEqualTo(member.getMemberId());
        assertThat(response.memberName()).isEqualTo("master");
        assertThat(response.email()).isEqualTo("sini@naver.com");
        assertThat(response.age()).isEqualTo(10);
    }
}