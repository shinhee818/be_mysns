package com.sini.mysns.api.controller.post;

import com.sini.mysns.api.controller.post.dto.FindPostCond;
import com.sini.mysns.api.controller.post.dto.FindPostResponse;
import com.sini.mysns.api.controller.post.dto.FindPostSortType;
import com.sini.mysns.api.controller.post.dto.FindPostsResponse;
import com.sini.mysns.api.service.redis.ViewCountService;
import com.sini.mysns.domain.PostCategory;
import com.sini.mysns.domain.post.Post;
import com.sini.mysns.domain.post.PostRepository;
import com.sini.mysns.global.exception.ApiException;
import com.sini.mysns.global.exception.ErrorCode;
import com.sini.mysns.repository.PostQuerydslRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/post/search")
@RestController
@Transactional(readOnly = true)
public class PostSearchController {

    private final PostQuerydslRepository postQuerydslRepository;
    private final PostRepository postRepository;
    private final ViewCountService viewCountService;

    @Transactional
    @GetMapping("/{postId}")
    public FindPostResponse findPost(@PathVariable("postId") Long postId,
                                     HttpServletRequest request,
                                     HttpServletResponse response)
    {
        Post post = postQuerydslRepository.findFetchPostById(postId)
                .orElseThrow(()-> new ApiException(ErrorCode.POST_NOT_FOUND));



        return FindPostResponse.from(post);
    }



    @GetMapping
    public FindPostsResponse findPosts(
            @RequestParam(value = "size", defaultValue = "12") int size,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "sort", defaultValue = "VIEWS", required = false) FindPostSortType sort,
            @RequestParam(value = "category", required = false) PostCategory postCategory,
            @RequestParam(value = "memberId", required = false) Long memberId
    ) {
        FindPostCond cond = new FindPostCond(
                PageRequest.of(page,size),
                postCategory,
                sort,
                null
        );
        return postQuerydslRepository.findPosts(cond);
    }
}