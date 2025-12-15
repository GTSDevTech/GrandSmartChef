package com.grandchefsupreme.mapper;


import com.grandchefsupreme.dto.RecipeStepDTO;

import com.grandchefsupreme.model.RecipeStep;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeStepMapper {

    RecipeStepDTO toDTO(RecipeStep recipeStep);
    RecipeStep toEntity(RecipeStepDTO recipeStepDTO);

    List<RecipeStepDTO> toDTO(List<RecipeStep> steps);
    List<RecipeStep> toEntity(List<RecipeStepDTO> dtos);

}
