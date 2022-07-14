package com.recipe2plate.api.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateRecipeRequest {

    private String recipeName;

    private String description;

    private MultipartFile previewImage;

    private MultipartFile previewVideo;

//    private List<Long> categories;

    private List<CreateIngredientRequest> ingredients;

    private List<CreateInstructionRequest> instructions;
}
