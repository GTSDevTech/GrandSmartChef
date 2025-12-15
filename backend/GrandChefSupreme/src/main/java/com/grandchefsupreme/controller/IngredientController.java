package com.grandchefsupreme.controller;

import com.grandchefsupreme.dto.IngredientCategoryDTO;
import com.grandchefsupreme.dto.IngredientDTO;
import com.grandchefsupreme.service.IngredientService;
import com.grandchefsupreme.utils.ApiResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/ingredient")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping("/categories")
    public List<IngredientCategoryDTO> listAllCategories(
            HttpServletRequest request
    ) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Categorías de ingredientes obtenidas correctamente"
        );

        return ingredientService.findAll();
    }

    @GetMapping("/by-category/{idCategory}")
    public List<IngredientDTO> listAllIngredientsByCategory(
            HttpServletRequest request,
            @Valid @PathVariable Long idCategory
    ) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Ingredientes obtenidos por categoría"
        );

        return ingredientService.findIngredientsByCategory(idCategory);
    }

    @GetMapping("/all")
    public List<IngredientDTO> listAllIngredients(
            HttpServletRequest request

    ){
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Listas ingredientes obtenidas"
        );
        return ingredientService.findAllIngredients();
    }

}