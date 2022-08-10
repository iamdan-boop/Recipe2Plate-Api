package com.recipe2plate.api.services;

import com.recipe2plate.api.dto.request.CreatePostRequest;
import com.recipe2plate.api.entities.AppUser;
import com.recipe2plate.api.entities.Post;
import com.recipe2plate.api.entities.Recipe;
import com.recipe2plate.api.exceptions.NoRecordFoundException;
import com.recipe2plate.api.repositories.PostRepository;
import com.recipe2plate.api.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final RecipeRepository recipeRepository;

    @Transactional
    public Post addNewPost(CreatePostRequest createPostRequest) {
        final Recipe referencedRecipe = recipeRepository
                .findById(createPostRequest.getReferencedRecipe())
                .orElseThrow(() -> new NoRecordFoundException("Recipe Referenced in Post not found."));

        final Post newPost = Post.builder()
                .description(createPostRequest.getDescription())
                .postPublisher( (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .referencedRecipe(referencedRecipe)
                .build();
        return postRepository.save(newPost);
    }
}
