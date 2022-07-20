package com.recipe2plate.api.dto.request;


import com.recipe2plate.api.dto.request.ingredient.CreateIngredientRequest;
import com.recipe2plate.api.dto.request.instruction.CreateInstructionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

    @NotNull
    @NotEmpty
    private List<Long> categories;


    @NotNull
    @NotEmpty
    private List<CreateIngredientRequest> ingredients;


    @NotNull
    @NotEmpty
    private List<CreateInstructionRequest> instructions;
}
