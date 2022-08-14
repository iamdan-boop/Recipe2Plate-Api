package com.recipe2plate.api.services;


import com.recipe2plate.api.dto.request.instruction.CreateInstructionRequest;
import com.recipe2plate.api.dto.request.instruction.UpdateInstructionRequest;
import com.recipe2plate.api.dto.response.InstructionDto;
import com.recipe2plate.api.entities.Instruction;
import com.recipe2plate.api.entities.Recipe;
import com.recipe2plate.api.mapper.InstructionMapper;
import com.recipe2plate.api.repositories.InstructionRepository;
import com.recipe2plate.api.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InstructionService {

    private final InstructionRepository instructionRepository;
    private final RecipeRepository recipeRepository;
    private final InstructionMapper instructionMapper;
    private final FileSystemService fileSystemService;

    public InstructionService(InstructionRepository instructionRepository,
                              RecipeRepository recipeRepository,
                              InstructionMapper instructionMapper,
                              FileSystemService fileSystemService) {
        this.instructionRepository = instructionRepository;
        this.recipeRepository = recipeRepository;
        this.instructionMapper = instructionMapper;
        this.fileSystemService = fileSystemService;
    }

    public List<InstructionDto> allInstructions(Long recipeId) {
        return instructionRepository.findAllByRecipeId(recipeId)
                .stream()
                .map(instructionMapper::toInstructionDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addInstruction(CreateInstructionRequest createInstructionRequest,
                               Recipe recipe) {
        final Instruction instruction = instructionMapper.toInstructionEntity(createInstructionRequest);
        recipe.getInstructions().add(instruction);

        recipeRepository.save(recipe);
        instructionMapper.toInstructionDto(instruction);
    }

    public InstructionDto updateInstruction(UpdateInstructionRequest updateInstructionRequest,
                                            Instruction instruction) {

        instruction.setInstruction(updateInstructionRequest.getInstruction());
        instruction.setName(updateInstructionRequest.getName());

        return instructionMapper.toInstructionDto(instructionRepository.save(instruction));
    }


    public void updateInstructionMedia(MultipartFile instructionMedia,
                                       Instruction instruction) throws Exception {
        final String fileName = fileSystemService.saveImage(instructionMedia);

        instruction.setImageUrl(fileName);
        instructionMapper.toInstructionDto(instructionRepository.save(instruction));
    }


    public void deleteInstruction(Instruction instruction) {
        instructionRepository.delete(instruction);
    }
}


