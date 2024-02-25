package com.sini.mysns.domain.member;

import com.google.common.collect.Lists;
import com.sini.mysns.domain.BaseTimeEntity;
import com.sini.mysns.global.config.security.MemberRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String memberName;

    private String password;

    @Column(nullable = false)
    private int age;

    private String url;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "member_roles_mapping", joinColumns = @JoinColumn(name = "member_id"))
    private List<MemberRole> memberRoles = new ArrayList<>();

    public boolean matchPassword(String pw, PasswordEncoder passwordEncoder)
    {
        return passwordEncoder.matches(pw, this.password);
    }

    @Builder
    public Member(
            Long memberId,
            String memberName,
            String email,
            int age,
            String url,
            String password,
            List<MemberRole> memberRoles
    ) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.email = email;
        this.age = age;
        this.url = url;
        this.password = password;
        this.memberRoles = memberRoles;
    }

    public static Member join(
            String email,
            String memberName,
            int age,
            String url,
            String password,
            PasswordEncoder passwordEncoder
    ) {
        return Member.builder()
                .email(email)
                .memberName(memberName)
                .age(age)
                .url(url)
                .password(passwordEncoder.encode(password))
                .memberRoles(Lists.newArrayList(MemberRole.ADMIN))
                .build();
    }

    public void update(Member updateMember)
    {
        setMemberName(updateMember.getMemberName());
        setAge(updateMember.getAge());
    }

    private void setMemberName(String memberName)
    {
        if (memberName == null || memberName.isBlank())
        {
            return ;
        }

        this.memberName = memberName;
    }

    private void setAge(int age)
    {
        if(age <= 0)
        {
            return ;
        }

        this.age = age;
    }
}
