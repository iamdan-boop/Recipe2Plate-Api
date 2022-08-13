package com.recipe2plate.api.dto.response;


import lombok.Data;

@Data
public class CommentDto {

    private Long id;

    private String comment;

    private AppUserDto commentBy;
}
