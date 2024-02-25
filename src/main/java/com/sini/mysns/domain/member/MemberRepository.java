package com.sini.mysns.domain.member;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    @Query("select m from Member m where m.memberId = :memberId")
    Optional<Member> findMember(@Param("memberId") Long memberId);
    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = {"memberRoles"})
    @Query("select m from Member m where m.email = :email")
    Member getWithRoles(@Param("email") String email);
}
