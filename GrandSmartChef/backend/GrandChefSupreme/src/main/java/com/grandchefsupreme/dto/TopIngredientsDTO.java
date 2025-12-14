package com.grandchefsupreme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopIngredientsDTO {

    private String ingredientName;
    private Long recipeCount;


}
