    package com.grandchefsupreme.controller;

    import com.grandchefsupreme.dto.RecipeCardDTO;
    import com.grandchefsupreme.dto.RecipeDTO;
    import com.grandchefsupreme.service.RecipeService;
    import com.grandchefsupreme.utils.ApiResponseMessage;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.MediaType;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.IOException;
    import java.util.List;


    @RestController
    @RequestMapping(value = "/api/recipes")
    @RequiredArgsConstructor
    public class    RecipeController {

        private final RecipeService recipeService;

    @GetMapping("/card")
    public List<RecipeCardDTO> listAllCards(HttpServletRequest request) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Recetas activas obtenidas correctamente"
                );

        return recipeService.getAllActiveRecipesForCards();
    }

    @GetMapping("/create/all")
    public List<RecipeCardDTO> listAllRecipes(HttpServletRequest request) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Listado completo de recetas"
        );

        return recipeService.getAllRecipes();
    }


    @GetMapping("/detail")
    public RecipeDTO detailById(
            HttpServletRequest request,
            @Valid @RequestParam Long id
    ) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Detalle de la receta obtenido correctamente"
        );

        return recipeService.getRecipeForDetails(id);
    }

    @GetMapping("/create/detail")
    public RecipeDTO detailByIdCreate(
            HttpServletRequest request,
            @Valid @RequestParam Long id
    ) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Detalle de la receta (modo edición)"
        );

        return recipeService.getRecipeForDetails(id);
    }


    @PostMapping(
            value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public RecipeDTO createRecipe(
            HttpServletRequest request,
            @RequestPart("recipe") @Valid RecipeDTO recipeDTO,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Receta creada correctamente"
        );

        return recipeService.createRecipe(recipeDTO, image);
    }


        @PutMapping(
                value = "/update",
                consumes = MediaType.MULTIPART_FORM_DATA_VALUE
        )
        public RecipeDTO editRecipe(
                HttpServletRequest request,
                @RequestPart("recipe") @Valid RecipeDTO recipeDTO,
                @RequestPart(value = "image", required = false) MultipartFile image
        ) throws IOException {

            request.setAttribute(
                    ApiResponseMessage.MESSAGE_ATTR,
                    "Receta actualizada correctamente"
            );

            return recipeService.updateRecipe(recipeDTO, image);
        }


        @DeleteMapping("/{id}")
    public void deleteRecipe(
            HttpServletRequest request,
            @Valid @PathVariable Long id
    ) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Receta eliminada correctamente"
        );

        recipeService.deleteRecipe(id);
    }

    @GetMapping("/user-preferences/{userId}")
    public List<RecipeCardDTO> findByUserId(
            HttpServletRequest request,
            @Valid @PathVariable Long userId
    ) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Recetas obtenidas según preferencias del usuario"
        );

        return recipeService.searchRecipes(userId, null);
    }


    @GetMapping("/ingredients")
    public List<RecipeCardDTO> findByIngredients(
            HttpServletRequest request,
            @Valid @RequestParam("ingredientIds") List<Long> ingredientIds
    ) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Recetas filtradas por ingredientes"
        );

        return recipeService.searchRecipes(null, ingredientIds);
    }

    @GetMapping("/search")
    public List<RecipeCardDTO> search(
            HttpServletRequest request,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) List<Long> ingredientIds
    ) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Recetas filtradas correctamente"
        );

        return recipeService.searchRecipes(userId, ingredientIds);
    }
}