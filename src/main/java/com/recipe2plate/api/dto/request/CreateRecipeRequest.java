package com.recipe2plate.api.dto.request;


import com.recipe2plate.api.dto.request.ingredient.CreateIngredientRequest;
import com.recipe2plate.api.dto.request.instruction.CreateInstructionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class CreateRecipeRequest {

    @NotNull
    @NotEmpty
    private String recipeName;


    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    private MultipartFile previewImage;


    @NotNull
    private MultipartFile previewVideo;

    @Size(min = 1)
    @NotNull
    private List<Long> categories;


    @Size(min = 1)
    @NotNull
    private List<@Valid CreateIngredientRequest> ingredients;

    @Size(min = 1)
    @NotNull
    private List<@Valid CreateInstructionRequest> instructions;
}
