package com.grandchefsupreme.mapper;


import com.grandchefsupreme.dto.IngredientCategoryDTO;
import com.grandchefsupreme.model.IngredientCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IngredientCategoryMapper {


    IngredientCategoryDTO toDTO(IngredientCategory ingredientCategory);

    IngredientCategory toEntity(IngredientCategoryDTO ingredientCategoryDTO);

    List<IngredientCategoryDTO> toDTO(List<IngredientCategory> ingredientCategoryList);

    List<IngredientCategory> toEntity(List<IngredientCategoryDTO> ingredientCategoryDTOList);
}
