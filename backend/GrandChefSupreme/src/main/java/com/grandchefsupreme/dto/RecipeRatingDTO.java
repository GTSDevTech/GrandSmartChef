package com.grandchefsupreme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data

public class RecipeRatingDTO {

    private Long id;
    private Long recipeId;
    private Long clientId;
    private Integer rating;
    private String review;


}
