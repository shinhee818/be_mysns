package com.sini.mysns.api.service.post;

import com.sini.mysns.api.controller.post.dto.CreatePostImageRequest;
import com.sini.mysns.api.service.post.dto.CreatePostServiceRequest;
import com.sini.mysns.api.service.post.dto.UpdatePostServiceRequest;
import com.sini.mysns.domain.PostCategory;
import com.sini.mysns.domain.member.Member;
import com.sini.mysns.domain.member.MemberRepository;
import com.sini.mysns.domain.post.Post;
import com.sini.mysns.domain.post.PostImage;
import com.sini.mysns.domain.post.PostRepository;
import com.sini.mysns.domain.post.PostTag;
import com.sini.mysns.domain.tag.Tag;
import com.sini.mysns.domain.tag.TagRepository;
import com.sini.mysns.global.config.security.AuthUtil;
import com.sini.mysns.global.exception.ApiException;
import com.sini.mysns.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostService {

    private final TagRepository tagRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public Long createPost(CreatePostServiceRequest request)
    {
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(()-> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .member(member)
                .postCategory(PostCategory.BACKEND)
                .build();

        List<Tag> tags = createNewTags(request.tagList());
        List<PostTag> postTags = tags.stream()
                .map(tag ->
                            PostTag.builder()
                                .post(post)
                                .tag(tag)
                                .build()
                )
                .toList();

        for (PostTag postTag : postTags)
        {
            post.addPostTag(postTag);
        }

        List<PostImage> postImages= createNewPosts(request.postImages());
        for(PostImage postImage : postImages)
        {
            post.addPostImage(postImage);
        }
        return postRepository.save(post).getPostId();
    }

    private List<Tag> createNewTags(List<String> requestTagList)
    {
        List<Tag> findTags = tagRepository.findAllInContent(requestTagList);

        List<String> findTagContents = findTags.stream()
                .map(Tag::getTagContent)
                .toList();

        requestTagList.removeAll(findTagContents);

        List<Tag> newTags = requestTagList.stream()
                .map(tagContent -> Tag.builder().tagContent(tagContent).build())
                .collect(Collectors.toList());

        tagRepository.saveAll(newTags);
        findTags.addAll(newTags);
        return findTags;
    }

    private List<PostImage> createNewPosts(List<CreatePostImageRequest> createPostImageRequests)
    {
        return createPostImageRequests.stream()
                .map(request -> PostImage.builder()
                        .url(request.url())
                        .postImageOrder(request.postImageOrder())
                        .build())
                .collect(Collectors.toList());
    }

    public Long updatePost(UpdatePostServiceRequest request)
    {
        Post post = postRepository.findById(request.postId())
                .orElseThrow(()->new ApiException(ErrorCode.POST_NOT_FOUND));

        Post updatePost = Post.builder()
                .title(request.title())
                .content(request.content())
                .build();

        post.update(updatePost);
        return post.getPostId();
    }

    public void deletePost(Long postId)
    {
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new ApiException(ErrorCode.POST_NOT_FOUND));
        postRepository.delete(post);
    }
}
