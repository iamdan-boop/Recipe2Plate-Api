package com.recipe2plate.api.mapper;


import com.recipe2plate.api.dto.request.instruction.CreateInstructionRequest;
import com.recipe2plate.api.dto.response.InstructionDto;
import com.recipe2plate.api.entities.Instruction;
import com.recipe2plate.api.services.FileSystemService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = FileSystemService.class)
public interface InstructionMapper {


    @Mapping(target = "imageUrl", source = "instructionMedia", qualifiedByName = "mediaToUrl")
    Instruction toInstructionEntity(CreateInstructionRequest instructionDto);

    InstructionDto toInstructionDto(Instruction instruction);
}
