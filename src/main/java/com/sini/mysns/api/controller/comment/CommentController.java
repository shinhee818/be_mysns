package com.sini.mysns.api.controller.comment;

import com.sini.mysns.api.controller.comment.dto.CommentResponse;
import com.sini.mysns.api.controller.comment.dto.CreateCommentRequest;
import com.sini.mysns.api.service.comment.CommentService;
import com.sini.mysns.api.service.comment.UpdateCommentRequest;
import com.sini.mysns.domain.comment.CommentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping
    public Long createComment(@Valid @RequestBody CreateCommentRequest request)
    {
        return commentService.createComment(request.toServiceDto());
    }

    @PutMapping("/{commentId}")
    public Long updateComment(
            @PathVariable(value = "commentId") Long commentId,
            @Valid @RequestBody UpdateCommentRequest request)
    {
        return commentService.updateComment(request.toServiceDto(commentId));
    }

    @DeleteMapping("/{commentId}/{memberId}")
    public void deleteComment(
            @PathVariable(value = "commentId") Long commentId,
            @PathVariable(value = "memberId") Long memberId
    ) {
        commentService.deleteComment(commentId, memberId);
    }

    @GetMapping("/{postId}")
    public CommentResponse comments(@PathVariable(value = "postId") Long postId){
        return CommentResponse.from(commentRepository.findCommentsByPostId(postId));
    }
}
