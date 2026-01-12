package com.grandchefsupreme.mapper;

import com.grandchefsupreme.dto.RecipeCardDTO;
import com.grandchefsupreme.model.Recipe;
import com.grandchefsupreme.model.RecipeRating;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeCardMapper {

    RecipeCardDTO toDTO(Recipe recipe);

    @Mapping(target = "imageUrl", ignore = true)
    Recipe toEntity(RecipeCardDTO recipeCardDTO);

    List<RecipeCardDTO> toDTO(List<Recipe> recipes);

    @Mapping(target = "imageUrl", ignore = true)
    List<Recipe> toEntity(List<RecipeCardDTO> recipeCardDTOList);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRecipeFromDto(RecipeCardDTO dto, @MappingTarget Recipe entity);


}
