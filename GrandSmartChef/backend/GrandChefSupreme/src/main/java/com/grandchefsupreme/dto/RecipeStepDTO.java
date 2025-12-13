package com.grandchefsupreme.dto;

import lombok.Data;

@Data
public class RecipeStepDTO {

    private Long id;
    private Integer stepNumber;
    private String instruction;
}
