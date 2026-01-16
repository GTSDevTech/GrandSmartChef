package com.grandchefsupreme.dto;

import com.grandchefsupreme.model.Enums.Unit;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ShoppingListIngredientDTO {


    private Long id;

    @NotBlank(message = "La receta no puede ser nula")
    private Long recipeId;
    @NotBlank(message = "Debe tener al menos un ingrediente")
    private Long ingredientId;

    private String ingredientName;
    private String recipeName;
    private BigDecimal quantity;
    private Unit unit;
    private Boolean bought;

}
