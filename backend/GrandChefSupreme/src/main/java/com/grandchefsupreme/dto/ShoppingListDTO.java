package com.grandchefsupreme.dto;

import lombok.Data;
import java.util.List;

@Data
public class ShoppingListDTO {

    private Long id;
    private Long clientId;
    private Boolean status;

    private List<ShoppingListIngredientDTO> items;


}
