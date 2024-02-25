package com.sini.mysns.api.controller.postLike;

import com.sini.mysns.api.controller.postLike.dto.PostLikeRequest;
import com.sini.mysns.api.service.like.PostLikeService;
import com.sini.mysns.domain.PostLike.PostLikeRepository;
import com.sini.mysns.global.config.security.AuthUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/like")
@RestController
public class PostLikeController {

    private final PostLikeService postLikeService;
    private final PostLikeRepository postLikeRepository;

    @PostMapping
    public void like(@Valid @RequestBody PostLikeRequest request)
    {
        postLikeService.like(request.toServiceDto());
    }

    @GetMapping
    public List<Long> findPostLike()
    {
        Long sini = AuthUtil.currentUserId();

        System.out.println("sini = " + sini);
        return postLikeService.findLikedPostIds(sini);
    }

    @GetMapping("/{postId}")
    public Optional<Long> postLikeCount(@PathVariable("postId") Long postId)
    {
        Optional<Long> postLike = postLikeRepository.countLikesByPostId(postId);
        return postLike;
    }

    @GetMapping("/member/{memberId}")
    public List<Long> findMemberPostLike(@PathVariable("memberId") Long memberId)
    {
        List<Long> likedPostIds = postLikeService.findLikedPostIds(memberId);
        return likedPostIds;
    }
}
