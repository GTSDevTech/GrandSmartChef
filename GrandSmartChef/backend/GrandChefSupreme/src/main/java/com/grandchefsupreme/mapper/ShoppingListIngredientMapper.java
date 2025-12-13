package com.grandchefsupreme.mapper;


import com.grandchefsupreme.dto.ShoppingListIngredientDTO;
import com.grandchefsupreme.model.ShoppingListIngredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShoppingListIngredientMapper {

    @Mapping(target = "recipeId", source = "recipe.id")
    @Mapping(target = "recipeName", source = "recipe.name")
    @Mapping(target = "ingredientId", source = "ingredient.id")
    @Mapping(target = "ingredientName", source = "ingredient.name")
    ShoppingListIngredientDTO toDTO(ShoppingListIngredient shoppingListIngredient);

    ShoppingListIngredient toEntity(ShoppingListIngredientDTO shoppingListIngredientDTO);

    @Mapping(target = "recipeId", source = "recipe.id")
    @Mapping(target = "recipeName", source = "recipe.name")
    @Mapping(target = "ingredientId", source = "ingredient.id")
    @Mapping(target = "ingredientName", source = "ingredient.name")
    List<ShoppingListIngredientDTO> toDTO (List<ShoppingListIngredient> shoppingListIngredients );

    List<ShoppingListIngredient> toEntity (List<ShoppingListIngredientDTO> shoppingListIngredientDTOs);


}
