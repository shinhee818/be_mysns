package com.sini.mysns.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("select c from Comment c join fetch c.member where c.member.memberId = :memberId and c.commentId = :commentId")
    Optional<Comment> findByMemberIdAndCommentId(@Param("memberId") Long memberId, @Param("commentId") Long commentId);

    @Query("select c from Comment c join fetch c.post where c.post.postId = :postId")
    List<Comment> findCommentsByPostId(@Param("postId") Long postId);
}
