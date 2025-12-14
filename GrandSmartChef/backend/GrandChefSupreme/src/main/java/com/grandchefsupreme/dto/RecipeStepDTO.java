package com.grandchefsupreme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeStepDTO {

    private Long id;
    private Integer stepNumber;
    private String instruction;
}
