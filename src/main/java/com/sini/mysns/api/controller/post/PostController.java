package com.sini.mysns.api.controller.post;

import com.sini.mysns.api.controller.post.dto.CreatePostRequest;
import com.sini.mysns.api.controller.post.dto.UpdatePostRequest;
import com.sini.mysns.api.service.post.PostService;
import com.sini.mysns.global.config.security.AuthUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public Long createPost(@Valid @RequestBody CreatePostRequest request)
    {
        return postService.createPost(request.toServiceDto());
    }

    @PutMapping("/{postId}")
    public Long updatePost(
            @PathVariable(value="postId") Long postId,
            @RequestBody @Valid UpdatePostRequest request
    ) {
        return postService.updatePost(request.toServiceDto(postId));
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable(value = "postId") @Valid Long postId)
    {
        postService.deletePost(postId);
    }
}
