    package com.grandchefsupreme.controller;

    import com.grandchefsupreme.dto.RecipeCardDTO;
    import com.grandchefsupreme.dto.RecipeDTO;
    import com.grandchefsupreme.service.RecipeService;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.AccessDeniedException;
    import org.springframework.web.bind.annotation.*;

    import java.util.Collections;
    import java.util.List;
    import java.util.Map;

    @RestController
    @RequestMapping(value = "/api/recipes")
    @RequiredArgsConstructor
    public class RecipeController {

        private final RecipeService recipeService;

    @GetMapping("/card")
    public ResponseEntity<List<RecipeCardDTO>> listAllCards(){
            List<RecipeCardDTO> recipes = recipeService.getAllActiveRecipesForCards();
            return ResponseEntity.ok(recipes);
    }

    @GetMapping("/create/all")
    public ResponseEntity<List<RecipeCardDTO>> listAllRecipes(){
        List<RecipeCardDTO> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }


    @GetMapping("/detail")
    public ResponseEntity<?> detailById(@Valid @RequestParam Long id){
            RecipeDTO recipe = recipeService.getRecipeForDetails(id);
            return ResponseEntity.ok(recipe);
    }

    @GetMapping("/create/detail")
    public ResponseEntity<?> detailByIdCreate(@Valid @RequestParam Long id){
        RecipeDTO recipe = recipeService.getRecipeForDetails(id);
        return ResponseEntity.ok(recipe);
    }


        @PostMapping("/create")
    public ResponseEntity<RecipeDTO> createRecipe(@Valid @RequestBody RecipeDTO recipeDTO) {
        RecipeDTO createdRecipe = recipeService.createRecipe(recipeDTO);
        return ResponseEntity.ok(createdRecipe);
    }



    @PutMapping("/update")
    public ResponseEntity<RecipeDTO> editRecipe(@Valid @RequestBody RecipeDTO recipeDTO) {
        RecipeDTO updateRecipe = recipeService.updateRecipe(recipeDTO);
        return ResponseEntity.ok(updateRecipe);
    }

    @DeleteMapping("/{id}")
    public void deleteRecipe(@Valid @PathVariable("id") Long id){
        recipeService.deleteRecipe(id);
    }

    @GetMapping("/user-preferences/{userId}")
    public ResponseEntity<List<RecipeCardDTO>> findByUserId(@Valid @PathVariable("userId") Long userId) {
        List<RecipeCardDTO> recipes = recipeService.getByUserPreferences(userId);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/ingredients")
    public ResponseEntity<List<RecipeCardDTO>> findByIngredients(@Valid @RequestParam("ingredientIds") List<Long> ingredientIds) {
        List<RecipeCardDTO> recipes = recipeService.getByIngredientIds(ingredientIds);
        return ResponseEntity.ok(recipes);
    }

}
