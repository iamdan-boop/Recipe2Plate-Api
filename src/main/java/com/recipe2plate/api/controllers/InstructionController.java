package com.recipe2plate.api.controllers;


import com.recipe2plate.api.dto.request.instruction.CreateInstructionRequest;
import com.recipe2plate.api.dto.request.instruction.UpdateInstructionRequest;
import com.recipe2plate.api.dto.response.InstructionDto;
import com.recipe2plate.api.services.InstructionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/instructions")
@RequiredArgsConstructor
public class InstructionController {
    public final InstructionService instructionService;

    @GetMapping("/{recipeId}")
    public ResponseEntity<List<InstructionDto>> allInstructions(@PathVariable Long recipeId) {
        final List<InstructionDto> allInstructions = instructionService.allInstructions(recipeId);
        return ResponseEntity.ok().body(allInstructions);
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/addInstruction/{recipeId}")
    public ResponseEntity<InstructionDto> addInstruction(@Valid
                                                         @ModelAttribute CreateInstructionRequest createInstructionRequest,
                                                         @PathVariable Long recipeId) {
        final InstructionDto addInstruction = instructionService.addInstruction(createInstructionRequest, recipeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(addInstruction);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/updateInstruction/{instructionId}")
    public ResponseEntity<InstructionDto> updateInstruction(@PathVariable Long instructionId,
                                                            @Valid @ModelAttribute UpdateInstructionRequest updateInstructionRequest) {
        final InstructionDto updatedInstruction = instructionService.updateInstruction(updateInstructionRequest, instructionId);
        return ResponseEntity.accepted().body(updatedInstruction);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/updateInstructionMedia/{instructionId}")
    public ResponseEntity<?> updateInstructionMedia(@PathVariable Long instructionId,
                                                    @RequestParam MultipartFile instructionMedia) throws Exception {
        instructionService.updateInstructionMedia(instructionMedia, instructionId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/deleteInstruction/{instructionId}")
    public ResponseEntity<?> deleteInstruction(@PathVariable Long instructionId) {
        instructionService.deleteInstruction(instructionId);
        return ResponseEntity.noContent().build();
    }
}
