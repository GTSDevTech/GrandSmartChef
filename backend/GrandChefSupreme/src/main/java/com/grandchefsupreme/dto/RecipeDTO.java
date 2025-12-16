package com.grandchefsupreme.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "La dificultad es obligatoria")
    private String difficulty;

    @NotNull(message = "El número de raciones es obligatorio")
    private Integer servings;

    @NotNull(message = "El tiempo de preparación es obligatorio")
    private Double prepTime;

    private Double averageRating;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    private String imageUrl;

    @NotEmpty(message = "Debe tener al menos una etiqueta")
    private Set<TagDTO> tags;

    @NotEmpty(message = "Debe tener ingredientes")
    private List<RecipeIngredientDTO> ingredients;

    @NotEmpty(message = "Debe tener pasos")
    private List<RecipeStepDTO> steps;

    private NutritionInfoDTO nutritionInfo;

}
