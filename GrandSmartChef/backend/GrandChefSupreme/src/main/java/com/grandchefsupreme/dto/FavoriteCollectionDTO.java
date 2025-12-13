package com.grandchefsupreme.dto;
import lombok.Data;
import java.sql.Timestamp;
import java.util.List;

@Data
public class FavoriteCollectionDTO {

    private Long id;
    private String title;
    private String color;
    private Boolean isActive;
    private Long clientId;
    private List<RecipeCardDTO> recipes;

}
