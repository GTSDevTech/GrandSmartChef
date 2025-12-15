package com.grandchefsupreme.dto;

import lombok.Data;

import java.util.Set;

@Data
public class RecipeCardDTO {
    private Long id;
    private String name;
    private String imageUrl;
    private Double averageRating;
    private Integer servings;
    private Double prepTime;
    private String difficulty;
    private Set<TagDTO> tags;

}
