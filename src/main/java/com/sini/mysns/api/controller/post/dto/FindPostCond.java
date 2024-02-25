package com.sini.mysns.api.controller.post.dto;

import com.sini.mysns.domain.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
@Getter
@AllArgsConstructor
public class FindPostCond {
    private Pageable pageable;
    private PostCategory postCategory;
    private FindPostSortType postSortType;
    private Long findMemberId;
}
