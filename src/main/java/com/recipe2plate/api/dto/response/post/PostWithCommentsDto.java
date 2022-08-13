package com.recipe2plate.api.dto.response.post;

import com.recipe2plate.api.dto.response.AppUserDto;
import com.recipe2plate.api.dto.response.CommentDto;
import lombok.Data;

import java.util.List;

@Data
public class PostWithCommentsDto {

    private Long id;

    private String description;

    private AppUserDto postPublisher;

    private List<CommentDto> comments;
}
