package com.sini.mysns.api.service.comment;

import com.sini.mysns.api.service.comment.dto.CreateCommentServiceRequest;
import com.sini.mysns.api.service.comment.dto.UpdateCommentServiceRequest;
import com.sini.mysns.domain.comment.Comment;
import com.sini.mysns.domain.comment.CommentRepository;
import com.sini.mysns.domain.member.Member;
import com.sini.mysns.domain.member.MemberRepository;
import com.sini.mysns.domain.post.Post;
import com.sini.mysns.domain.post.PostRepository;
import com.sini.mysns.global.exception.ApiException;
import com.sini.mysns.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public Long createComment(CreateCommentServiceRequest request)
    {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(()-> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.findById(request.postId())
                .orElseThrow(()->new ApiException(ErrorCode.POST_NOT_FOUND));

        Comment comment = Comment.builder()
                .comment(request.comment())
                .member(member)
                .post(post)
                .build();

        return commentRepository.save(comment).getCommentId();
    }

    public Long updateComment(UpdateCommentServiceRequest request)
    {
        Comment comment = commentRepository.findByMemberIdAndCommentId(request.memberId(), request.commentId())
                .orElseThrow(() -> new ApiException(ErrorCode.COMMNET_MEMBER_NOT_EQAUL));

        comment.changeContent(request.comment());
        return comment.getCommentId();
    }

    private static void validateUpdateComment(long postId, long memberId, Comment comment)
    {
        Post post = comment.getPost();

        if (post.getPostId() == postId)
        {
            throw new IllegalStateException("요청과 다른 Post ID");
        }

        Member member = comment.getMember();

        if (member.getMemberId() == memberId)
        {
            throw new IllegalStateException("작성자만 수정할 수 있다.");
        }
    }

    public void deleteComment(Long commentId, Long memberId)
    {
        Comment comment = commentRepository.findByMemberIdAndCommentId(memberId, commentId)
                .orElseThrow(() -> new ApiException(ErrorCode.COMMNET_MEMBER_NOT_EQAUL));
        
        commentRepository.delete(comment);
    }
}
