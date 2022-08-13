package com.recipe2plate.api.mapper;


import com.recipe2plate.api.dto.response.CommentDto;
import com.recipe2plate.api.entities.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDto toCommentDto(Comment comment);
}
