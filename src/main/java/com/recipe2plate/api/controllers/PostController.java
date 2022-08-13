package com.recipe2plate.api.controllers;


import com.recipe2plate.api.dto.request.CreatePostRequest;
import com.recipe2plate.api.dto.request.UpdatePostRequest;
import com.recipe2plate.api.dto.response.post.PostDto;
import com.recipe2plate.api.entities.AppUser;
import com.recipe2plate.api.entities.Post;
import com.recipe2plate.api.exceptions.NoRecordFoundException;
import com.recipe2plate.api.services.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/post")
@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@Slf4j
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping("/")
    public ResponseEntity<List<PostDto>> allPosts() {
        final List<PostDto> allPosts = postService.allPosts();
        return ResponseEntity.ok().body(allPosts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> findPost(@PathVariable Long postId) {
        final PostDto findPost = postService.findPost(postId);
        return ResponseEntity.ok().body(findPost);
    }

    @PostMapping("/addNewPost")
    public ResponseEntity<?> addNewPost(@Valid
                                        @RequestBody CreatePostRequest createPostRequest,
                                        @AuthenticationPrincipal AppUser postPublisher) {
        postService.addNewPost(createPostRequest, postPublisher);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/updatePost/{post}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Optional<Post> post,
                                           @Valid
                                           @RequestBody UpdatePostRequest updatePostRequest) {
        if (post.isEmpty()) throw new NoRecordFoundException("Post not found.");

        final PostDto updatedPost = postService.updatePost(post.get(), updatePostRequest);
        return ResponseEntity.accepted().body(updatedPost);
    }


    @DeleteMapping("/deletePost/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
