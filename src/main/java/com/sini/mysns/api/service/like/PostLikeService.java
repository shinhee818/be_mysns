package com.sini.mysns.api.service.like;

import com.sini.mysns.api.service.like.dto.PostLikeServiceRequest;
import com.sini.mysns.domain.postlike.PostLike;
import com.sini.mysns.domain.postlike.PostLikeRepository;
import com.sini.mysns.domain.member.Member;
import com.sini.mysns.domain.member.MemberRepository;
import com.sini.mysns.domain.post.Post;
import com.sini.mysns.domain.post.PostRepository;
import com.sini.mysns.global.exception.ApiException;
import com.sini.mysns.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public void like(PostLikeServiceRequest request)
    {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(()-> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.findById(request.postId())
                .orElseThrow(()->new ApiException(ErrorCode.POST_NOT_FOUND));

        PostLike postLike = PostLike.builder().member(member).post(post).build();
        postLikeRepository.save(postLike);
    }

    public List<Long> findLikedPostIds(Long memberId)
    {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        List<PostLike> likedPosts = postLikeRepository.findByMember(member);

        return likedPosts.stream()
                .map(postLike -> postLike.getPost().getPostId())
                .collect(Collectors.toList());
    }
}
