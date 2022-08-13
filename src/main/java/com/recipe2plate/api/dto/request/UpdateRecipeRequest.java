package com.recipe2plate.api.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UpdateRecipeRequest {

    @NotNull
    @NotEmpty
    private String recipeName;

    @NotNull
    @NotEmpty
    private String description;

    @Nullable
    private MultipartFile previewImage;

    @Nullable
    private MultipartFile previewVideo;
}
