package com.grandchefsupreme.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class ShoppingListDTO {

    private Long id;

    @NotBlank(message = "Usuario no logueado")
    private Long clientId;
    private Boolean status;

    @NotEmpty(message = "Debe contener ingredientes")
    private List<ShoppingListIngredientDTO> items;


}
