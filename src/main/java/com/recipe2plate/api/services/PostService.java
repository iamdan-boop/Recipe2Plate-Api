package com.recipe2plate.api.services;

import com.recipe2plate.api.dto.request.CreatePostRequest;
import com.recipe2plate.api.dto.request.UpdatePostRequest;
import com.recipe2plate.api.dto.response.post.PostDto;
import com.recipe2plate.api.entities.AppUser;
import com.recipe2plate.api.entities.Post;
import com.recipe2plate.api.entities.Recipe;
import com.recipe2plate.api.exceptions.NoRecordFoundException;
import com.recipe2plate.api.mapper.PostMapper;
import com.recipe2plate.api.repositories.PostRepository;
import com.recipe2plate.api.repositories.RecipeRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final RecipeRepository recipeRepository;

    private final PostMapper postMapper;

    public PostService(PostRepository postRepository,
                       RecipeRepository recipeRepository,
                       PostMapper postMapper) {
        this.postRepository = postRepository;
        this.recipeRepository = recipeRepository;
        this.postMapper = postMapper;
    }

    public List<PostDto> allPosts(Pageable pageable) {
        return this.postRepository.findAll(pageable)
                .stream()
                .map(postMapper::toPostDto)
                .collect(Collectors.toList());
    }


    public List<PostDto> searchPost(String query, Pageable page) {
        if (query == null || query.isEmpty() || query.isBlank()) {
            return Collections.emptyList();
        }
        return postRepository
                .searchPostsByDescriptionContaining(query, page)
                .stream()
                .map(postMapper::toPostDto)
                .collect(Collectors.toList());
    }


    public PostDto findPost(Long postId) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoRecordFoundException("Post not found."));
        return postMapper.toPostDto(post);
    }


    @Transactional
    public void addNewPost(CreatePostRequest createPostRequest, AppUser postPublisher) {
        final Recipe referencedRecipe = recipeRepository
                .findById(createPostRequest.getReferencedRecipe())
                .orElseThrow(() -> new NoRecordFoundException("Referenced recipe not found."));

        final Post newPost = Post.builder()
                .description(createPostRequest.getDescription())
                .postPublisher(postPublisher)
                .referencedRecipe(referencedRecipe)
                .build();
        postRepository.save(newPost);
    }


    public PostDto updatePost(Post post, UpdatePostRequest updatePostRequest) {
        post.setDescription(updatePostRequest.getDescription());

        if (updatePostRequest.getReferenceRecipe() == null) {
            return postMapper.toPostDto(postRepository.save(post));
        }
        recipeRepository.findById(updatePostRequest.getReferenceRecipe())
                .ifPresentOrElse(post::setReferencedRecipe, () -> {
                    throw new NoRecordFoundException("Recipe not found.");
                });
        return postMapper.toPostDto(postRepository.save(post));
    }


    public void deletePost(Long postId) {
        postRepository.findById(postId)
                .ifPresentOrElse(postRepository::delete, () -> {
                    throw new NoRecordFoundException("Post doesnt exists");
                });
    }
}
