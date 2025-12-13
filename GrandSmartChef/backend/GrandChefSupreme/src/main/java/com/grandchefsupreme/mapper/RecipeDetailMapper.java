package com.grandchefsupreme.mapper;

import com.grandchefsupreme.dto.RecipeCardDTO;
import com.grandchefsupreme.dto.RecipeDTO;
import com.grandchefsupreme.model.Recipe;
import org.mapstruct.*;
import java.util.List;


@Mapper(componentModel = "spring", uses = {
        RecipeCardMapper.class,
        RecipeIngredientMapper.class,
        RecipeStepMapper.class,
        IngredientMapper.class
})
public interface RecipeDetailMapper {


    @Mapping(source = "recipeIngredients", target = "ingredients")
    @Mapping(source = "recipeSteps", target = "steps")
    RecipeDTO toDto(Recipe recipe);


    @Mapping(source = "ingredients", target = "recipeIngredients")
    @Mapping(source = "steps", target = "recipeSteps")
    Recipe toEntity(RecipeDTO recipeDTO);


    @Mapping(source = "recipeIngredients", target = "ingredients")
    @Mapping(source = "recipeSteps", target = "steps")
    List<RecipeDTO> toDto(List<Recipe> recipes);


    @Mapping(source = "ingredients", target = "recipeIngredients")
    @Mapping(source = "steps", target = "recipeSteps")
    List<Recipe> toEntity(List<RecipeDTO> recipeDTOs);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRecipeFromDto(RecipeDTO dto, @MappingTarget Recipe entity);
}