package com.grandchefsupreme.dto;
import com.grandchefsupreme.model.Enums.Unit;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class IngredientDTO {

    private Long id;
    private String name;
    private BigDecimal calories;
    private BigDecimal proteins;
    private BigDecimal carbs;
    private BigDecimal fats;
    private String urlPhoto;
    private Unit unit;
    private IngredientCategoryDTO category;
}
