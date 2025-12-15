package com.grandchefsupreme.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.sql.Timestamp;
import java.util.List;

@Data
public class FavoriteCollectionDTO {

    @NotBlank(message = "Título de la colección obligatorio")
    private String title;

    @NotBlank(message = "Se debe seleccionar un color")
    private String color;

    private Boolean isActive;

    @NotNull(message = "Usuario no logueado")
    private Long clientId;

    private List<RecipeCardDTO> recipes;

}
