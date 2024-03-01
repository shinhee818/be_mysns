package com.sini.mysns.api.service.comment;

import com.sini.mysns.IntegrationTestSupporter;
import com.sini.mysns.api.service.comment.dto.CreateCommentServiceRequest;
import com.sini.mysns.api.service.comment.dto.UpdateCommentServiceRequest;
import com.sini.mysns.domain.comment.Comment;
import com.sini.mysns.domain.comment.CommentRepository;
import com.sini.mysns.domain.member.Member;
import com.sini.mysns.domain.member.MemberRepository;
import com.sini.mysns.domain.post.Post;
import com.sini.mysns.domain.post.PostRepository;
import com.sini.mysns.global.config.security.AuthUtil;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class CommentServiceTest extends IntegrationTestSupporter {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @DisplayName("댓글 생성")
    @Test
    void createComment()
    {
        Member member =  memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow();

        Post post = postRepository.save(
                Post.builder()
                        .title("title")
                        .content("content")
                        .member(member)
                        .build()
        );

        CreateCommentServiceRequest request = new CreateCommentServiceRequest(
                "comment",
                post.getPostId(),
                member.getMemberId()
        );

        Long commentId1 = commentService.createComment(request);

        assertThat(commentId1).isNotNull();
        List<Comment> comments = commentRepository.findAll();
        assertThat(comments).hasSize(1);
        Comment comment = comments.get(0);
        assertThat(comment.getComment()).isEqualTo("comment");

    }

    @DisplayName("댓글 수정")
    @Test
    void updateComment()
    {
        Member member =  memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow();

        Post post = postRepository.save(
                Post.builder()
                        .title("title")
                        .content("content")
                        .member(member)
                        .build()
        );

        Comment comment = commentRepository.save(
                Comment.builder()
                        .comment("comment")
                        .member(member)
                        .post(post)
                        .build()
        );

        UpdateCommentServiceRequest request = new UpdateCommentServiceRequest(
                "comment",
                comment.getCommentId(),
                member.getMemberId()
        );

        entityManager.flush();
        entityManager.clear();

        Long commentId = commentService.updateComment(request);
        assertThat(commentId).isNotNull();
        List<Comment> comments = commentRepository.findAll();
        assertThat(comments).hasSize(1);
        assertThat(comments.get(0))
                .extracting(Comment::getComment)
                .isEqualTo("comment");
    }

    @DisplayName("댓글 삭제")
    @Test
    void deleteComment()
    {
        Member member =  memberRepository.save(
                Member.builder()
                        .memberName("master1")
                        .email("sini0818@naver.com")
                        .age(10)
                        .url("tlsgml2@gmail.com")
                        .build()
        );

        Post post = postRepository.save(
                Post.builder()
                        .title("title")
                        .content("content")
                        .member(member)
                        .build()
        );

        Comment comment = commentRepository.save(
                Comment.builder()
                        .comment("comment")
                        .member(member)
                        .post(post)
                        .build()
        );

        entityManager.flush();
        entityManager.clear();

        commentService.deleteComment(comment.getCommentId(), comment.getMember().getMemberId());

        // Then
        Comment deletedComment = commentRepository.findById(comment.getCommentId()).orElse(null);
        assertThat(deletedComment).isNull();
    }
}