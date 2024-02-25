package com.sini.mysns.api.controller.post;

import com.sini.mysns.api.controller.post.dto.FindPostCond;
import com.sini.mysns.api.controller.post.dto.FindPostSortType;
import com.sini.mysns.api.controller.post.dto.FindPostsResponse;
import com.sini.mysns.global.config.security.AuthUtil;
import com.sini.mysns.repository.PostQuerydslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/my-post/search")
@RestController
public class MyPostSearchController {

    private final PostQuerydslRepository postQuerydslRepository;

    @GetMapping
    public FindPostsResponse findMyPosts(
            @RequestParam(value = "size", defaultValue = "12") int size,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "sort", defaultValue = "VIEWS", required = false) FindPostSortType sort
    ) {
        FindPostCond cond = new FindPostCond(
                PageRequest.of(page,size),
                null,
                sort,
                AuthUtil.currentUserId()
        );

        return postQuerydslRepository.findPosts(cond);
    }
}