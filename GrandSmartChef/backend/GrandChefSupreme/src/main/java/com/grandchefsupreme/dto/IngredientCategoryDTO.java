package com.grandchefsupreme.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientCategoryDTO {

    private Long id;
    private String name;
    private String description;
    private String photoUrl;


}
