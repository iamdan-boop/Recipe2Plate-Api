package com.recipe2plate.api.controllers;


import com.recipe2plate.api.dto.request.CreateCommentRequest;
import com.recipe2plate.api.dto.response.CommentDto;
import com.recipe2plate.api.entities.AppUser;
import com.recipe2plate.api.entities.Comment;
import com.recipe2plate.api.entities.Post;
import com.recipe2plate.api.exceptions.NoRecordFoundException;
import com.recipe2plate.api.services.CommentService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comment")
@PreAuthorize("hasRole('ROLE_USER')")
public class CommentController {

    private final CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> allCommentsByPost(@PathVariable Long postId,
                                                              @PageableDefault Pageable page) {
        final List<CommentDto> allCommentsByPost = commentService.allCommentsByPost(postId, page);
        return ResponseEntity.ok().body(allCommentsByPost);
    }

    @PostMapping("/addNewComment/{post}")
    public ResponseEntity<?> addNewComment(@PathVariable Optional<Post> post,
                                           @Valid @RequestBody CreateCommentRequest createCommentRequest,
                                           @AuthenticationPrincipal AppUser commentedBy) {
        if (post.isEmpty()) throw new NoRecordFoundException("Post not found, maybe its already deleted.");
        commentService.addNewComment(post.get(), createCommentRequest.getComment(), commentedBy);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/updateComment/{comment}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Optional<Comment> comment,
                                                    @Valid @RequestBody CreateCommentRequest updateCommentRequest) {
        if (comment.isEmpty()) throw new NoRecordFoundException("Comment not found.");
        final CommentDto updatedComment = commentService
                .updateComment(comment.get(), updateCommentRequest.getComment());
        return ResponseEntity.accepted().body(updatedComment);
    }


    @DeleteMapping("/deleteComment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
