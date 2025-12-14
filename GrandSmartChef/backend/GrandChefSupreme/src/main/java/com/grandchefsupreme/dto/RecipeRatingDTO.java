package com.grandchefsupreme.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class RecipeRatingDTO {

    private Long id;
    private Long recipeId;
    private String recipeName;
    private Long clientId;
    private Integer rating;
    private LocalDateTime ratingDate;
    private String review;


}
