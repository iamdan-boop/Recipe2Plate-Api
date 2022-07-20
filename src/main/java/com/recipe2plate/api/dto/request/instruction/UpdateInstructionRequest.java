package com.recipe2plate.api.dto.request.instruction;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
public class UpdateInstructionRequest {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String instruction;
}
