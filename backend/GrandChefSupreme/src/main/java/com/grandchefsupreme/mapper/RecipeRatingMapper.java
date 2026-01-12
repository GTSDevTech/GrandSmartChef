package com.grandchefsupreme.mapper;

import com.grandchefsupreme.dto.RecipeRatingDTO;
import com.grandchefsupreme.model.RecipeRating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeRatingMapper {

    RecipeRatingDTO toDTO(RecipeRating recipeRating);

    RecipeRating toEntity(RecipeRatingDTO recipeRatingDTO);

    List<RecipeRatingDTO> toDTO(List<RecipeRating> recipeRatings);

    List<RecipeRating> toEntity(List<RecipeRatingDTO> recipeRatingDTOS);
}
