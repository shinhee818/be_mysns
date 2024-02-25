package com.sini.mysns.domain.PostLike;

import com.sini.mysns.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface PostLikeRepository extends JpaRepository<PostLike,Long> {

    List<PostLike> findByMember(Member member);

    @Query("select count(pl.postLikeId) from PostLike pl" +
            " where pl.post.postId = :postId")
    Optional<Long> countLikesByPostId(@Param("postId") Long postId);

}
