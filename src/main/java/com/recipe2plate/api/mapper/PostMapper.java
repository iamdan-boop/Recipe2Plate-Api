package com.recipe2plate.api.mapper;

import com.recipe2plate.api.dto.response.post.PostDto;
import com.recipe2plate.api.dto.response.post.PostWithCommentsDto;
import com.recipe2plate.api.entities.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CommentMapper.class})
public interface PostMapper {

    PostDto toPostDto(Post post);

    PostWithCommentsDto toPostWithCommentsDto(Post post);
}
