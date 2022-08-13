package com.recipe2plate.api.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CreateCategoryRequest {
    @NotNull
    @NotEmpty
    private String categoryName;
}
