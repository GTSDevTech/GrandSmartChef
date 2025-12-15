package com.grandchefsupreme.dto;

import com.grandchefsupreme.model.Enums.Unit;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ShoppingListIngredientDTO {

    private Long id;
    private Long recipeId;
    private Long ingredientId;
    private String ingredientName;
    private String recipeName;
    private BigDecimal quantity;
    private Unit unit;
    private Boolean bought;

}
