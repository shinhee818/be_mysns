package com.sini.mysns.domain.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("select t from Tag t where t.tagContent in (:tagList)")
    List<Tag> findAllInContent(@Param("tagList") List<String> tagList);
}