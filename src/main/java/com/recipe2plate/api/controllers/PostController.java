package com.recipe2plate.api.controllers;


import com.recipe2plate.api.dto.request.CreatePostRequest;
import com.recipe2plate.api.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/post")
@RestController
@PreAuthorize("isAuthenticated() AND hasRole('ROLE_USER')")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/addNewPost")
    public ResponseEntity<?> addNewPost(@Valid
                                        @RequestBody CreatePostRequest createPostRequest) {
        postService.addNewPost(createPostRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
