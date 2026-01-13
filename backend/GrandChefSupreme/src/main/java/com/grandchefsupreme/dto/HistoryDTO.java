package com.grandchefsupreme.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class    HistoryDTO {

    private Long id;

    @NotNull(message = "Usuario no logueado")
    private Long clientId;

    @NotNull(message = "Receta obligatoria")
    private RecipeCardDTO recipe;

    @Pattern(
            regexp = "\\d{4}-\\d{2}-\\d{2}",
            message = "La fecha debe tener formato yyyy-MM-dd"
    )
    @NotNull(message = "Fecha obligatoria")
    private String date;

}
