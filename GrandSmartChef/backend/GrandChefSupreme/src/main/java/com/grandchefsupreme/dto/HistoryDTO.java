package com.grandchefsupreme.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class    HistoryDTO {

    @NotNull(message = "Usuario no logueado")
    private Long clientId;

    @NotNull(message = "Receta obligatoria")
    private Long recipeId;

    @NotNull(message = "Fecha obligatoria")
    private LocalDate date;

}
