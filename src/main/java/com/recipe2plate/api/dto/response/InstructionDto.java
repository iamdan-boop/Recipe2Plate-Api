package com.recipe2plate.api.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InstructionDto {
    private Long id;
    private String name;
    private String instruction;
    private String imageUrl;
}
