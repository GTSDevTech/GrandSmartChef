package com.grandchefsupreme.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class RecipeDTO {

    private Long id;
    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;
    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String difficulty;
    @NotNull
    private Integer servings;
    @NotNull
    private Double prepTime;
    @NotNull
    private Double averageRating;
    @NotNull
    @NotBlank
    private String description;
    @NotNull
    @NotBlank
    private String imageUrl;
    @NotNull
    @NotEmpty
    private Set<TagDTO> tags;
    @NotNull
    @NotEmpty
    private List<RecipeIngredientDTO> ingredients;
    @NotNull
    @NotEmpty
    private List<RecipeStepDTO> steps;

    private NutritionInfoDTO nutritionInfo;

}
