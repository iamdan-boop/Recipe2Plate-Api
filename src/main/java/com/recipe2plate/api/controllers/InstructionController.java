package com.recipe2plate.api.controllers;


import com.recipe2plate.api.dto.request.instruction.CreateInstructionRequest;
import com.recipe2plate.api.dto.request.instruction.UpdateInstructionRequest;
import com.recipe2plate.api.dto.response.InstructionDto;
import com.recipe2plate.api.entities.Instruction;
import com.recipe2plate.api.entities.Recipe;
import com.recipe2plate.api.exceptions.NoRecordFoundException;
import com.recipe2plate.api.services.InstructionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/instructions")
public class InstructionController {
    public final InstructionService instructionService;

    public InstructionController(InstructionService instructionService) {
        this.instructionService = instructionService;
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<List<InstructionDto>> allInstructions(@PathVariable Long recipeId) {
        final List<InstructionDto> allInstructions = instructionService.allInstructions(recipeId);
        return ResponseEntity.ok().body(allInstructions);
    }


    @PreAuthorize("hasRole('ROLE_USER') AND" +
            " @recipePermission.shouldAuthorizeDestructiveActions(#recipe)")
    @PostMapping("/addInstruction/{recipe}")
    public ResponseEntity<InstructionDto> addInstruction(@Valid
                                                         @ModelAttribute CreateInstructionRequest createInstructionRequest,
                                                         @PathVariable Optional<Recipe> recipe) {
        if (recipe.isEmpty()) throw new NoRecordFoundException("Recipe not found.");
        instructionService.addInstruction(createInstructionRequest, recipe.get());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ROLE_USER') AND " +
            "@instructionPermission.shouldAuthorizeDestructiveActions(#instructions)")
    @PutMapping("/updateInstruction/{instruction}")
    public ResponseEntity<InstructionDto> updateInstruction(@PathVariable Optional<Instruction> instruction,
                                                            @Valid @ModelAttribute UpdateInstructionRequest updateInstructionRequest) {
        if (instruction.isEmpty()) throw new NoRecordFoundException("Instruction not found.");
        final InstructionDto updatedInstruction = instructionService
                .updateInstruction(updateInstructionRequest, instruction.get());
        return ResponseEntity.accepted().body(updatedInstruction);
    }

    @PreAuthorize("hasRole('ROLE_USER') AND " +
            "@instructionPermission.shouldAuthorizeDestructiveActions(#instruction)")
    @PutMapping("/updateInstructionMedia/{instruction}")
    public ResponseEntity<?> updateInstructionMedia(@PathVariable Optional<Instruction> instruction,
                                                    @RequestParam MultipartFile instructionMedia) throws Exception {
        if (instruction.isEmpty()) throw new NoRecordFoundException("Instruction not found.");
        instructionService.updateInstructionMedia(instructionMedia, instruction.get());
        return ResponseEntity.accepted().build();
    }


    @PreAuthorize("hasRole('ROLE_USER') AND " +
            "@instructionPermission.shouldAuthorizeDestructiveActions(#instruction)")
    @DeleteMapping("/deleteInstruction/{instruction}")
    public ResponseEntity<?> deleteInstruction(@PathVariable Optional<Instruction> instruction) {
        if (instruction.isEmpty()) throw new NoRecordFoundException("Instruction not found.");
        instructionService.deleteInstruction(instruction.get());
        return ResponseEntity.noContent().build();
    }
}
