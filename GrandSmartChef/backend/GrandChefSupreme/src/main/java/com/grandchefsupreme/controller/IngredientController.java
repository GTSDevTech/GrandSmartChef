package com.grandchefsupreme.controller;

import com.grandchefsupreme.dto.IngredientCategoryDTO;
import com.grandchefsupreme.dto.IngredientDTO;
import com.grandchefsupreme.service.IngredientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<IngredientCategoryDTO>> listAllCategories(){
        List<IngredientCategoryDTO> categoryDTOS = ingredientService.findAll();
        return ResponseEntity.ok(categoryDTOS);
    }

    @GetMapping("/by-category/{idCategory}")
    public ResponseEntity<List<IngredientDTO>> listAllIngredientsByCategory(@Valid @PathVariable Long idCategory){
        List<IngredientDTO> ingredientDTOS = ingredientService.findIngredientsByCategory(idCategory);
        return ResponseEntity.ok(ingredientDTOS);
    }

}
