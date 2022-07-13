package com.recipe2plate.api.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull
@NotEmpty
public class CreateInstructionRequest {

    private String name;

    private String instruction;

    private MultipartFile instructionMedia;
}
