package com.grandchefsupreme.mapper;

import com.grandchefsupreme.dto.RecipeIngredientDTO;
import com.grandchefsupreme.model.RecipeIngredient;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",  uses = {IngredientMapper.class})
public interface RecipeIngredientMapper {

    @Mapping(source = "ingredient", target = "ingredient")
    RecipeIngredientDTO toDTO(RecipeIngredient recipeIngredient);

    @Mapping(source = "ingredient", target = "ingredient")
    RecipeIngredient toEntity(RecipeIngredientDTO recipeIngredientDTO);

    @Mapping(source = "ingredient", target = "ingredient")
    List<RecipeIngredientDTO> toDTO(List<RecipeIngredient> listEntityDTO);

    @Mapping(source = "ingredient", target = "ingredient")
    List<RecipeIngredient> toEntity(List<RecipeIngredientDTO> listEntity);

}
