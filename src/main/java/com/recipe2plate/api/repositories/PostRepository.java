package com.recipe2plate.api.repositories;

import com.recipe2plate.api.entities.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

    List<Post> searchPostsByDescriptionContaining(String query, Pageable page);
}
