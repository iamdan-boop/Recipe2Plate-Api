package com.recipe2plate.api.mapper;


import com.recipe2plate.api.dto.request.CreateInstructionRequest;
import com.recipe2plate.api.entities.Instruction;
import com.recipe2plate.api.services.FileSystemService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = FileSystemService.class)
public interface InstructionMapper {


    @Mapping(target = "imageUrl", source = "instructionMedia", qualifiedByName = "mediaToUrl")
    Instruction toInstructionEntity(CreateInstructionRequest instructionDto);
}
