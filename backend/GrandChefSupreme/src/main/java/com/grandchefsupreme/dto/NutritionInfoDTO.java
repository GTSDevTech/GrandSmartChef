package com.grandchefsupreme.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NutritionInfoDTO {

    private BigDecimal calories;
    private BigDecimal proteins;
    private BigDecimal carbs;
    private BigDecimal fats;
}
