package com.grandchefsupreme.dto;
import com.grandchefsupreme.model.Enums.Unit;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RecipeIngredientDTO {

    private String id;
    private BigDecimal quantity;
    private Unit unit;
    private IngredientDTO ingredient;

}


