package com.recipe2plate.api.services;


import com.recipe2plate.api.dto.response.CommentDto;
import com.recipe2plate.api.entities.AppUser;
import com.recipe2plate.api.entities.Comment;
import com.recipe2plate.api.entities.Post;
import com.recipe2plate.api.exceptions.NoRecordFoundException;
import com.recipe2plate.api.mapper.CommentMapper;
import com.recipe2plate.api.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;


    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }


    public List<CommentDto> allCommentsByPost(Long postId) {
        return commentRepository.findCommentsByPostId(postId)
                .stream()
                .map(commentMapper::toCommentDto)
                .collect(Collectors.toList());
    }


    public void addNewComment(Post post,
                              String comment,
                              AppUser commentedBy) {
        final Comment newComment = Comment.builder()
                .commentBy(commentedBy)
                .post(post)
                .comment(comment)
                .build();
        commentRepository.save(newComment);
    }


    public CommentDto updateComment(Comment comment, String updatedComment) {
        comment.setComment(updatedComment);
        return commentMapper.toCommentDto(commentRepository.save(comment));
    }


    public void deleteComment(Long commentId) {
        commentRepository.findById(commentId)
                .ifPresentOrElse(commentRepository::delete, () -> {
                    throw new NoRecordFoundException("Comment not found.");
                });
    }
}
