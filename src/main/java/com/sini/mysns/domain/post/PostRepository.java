package com.sini.mysns.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select distinct p from Post p " +
            "left join fetch p.member " +
            "left join fetch p.postImages " +
            "left join fetch p.postTagList pt " +
            "left join fetch pt.tag " +
            "where p.postId = :postId")
    Optional<Post> findOnePostById(@Param("postId") Long postId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.postId = :postId")
    void increateView(@Param("postId") Long postId);

    List<Post> findByPostIdIn(List<Long> postIds);
}
