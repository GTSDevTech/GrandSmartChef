package com.grandchefsupreme.mapper;

import com.grandchefsupreme.dto.IngredientDTO;
import com.grandchefsupreme.model.Ingredient;
import org.mapstruct.Mapper;



import java.util.List;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    IngredientDTO toDTO(Ingredient ingredient);

    Ingredient toEntity(IngredientDTO ingredientDTO);

    List<IngredientDTO> toDTO(List<Ingredient> ingredients);

    List<Ingredient> toEntity(List<IngredientDTO> ingredientDTOS);



}
