package com.grandchefsupreme.controller;

import com.grandchefsupreme.dto.ShoppingListDTO;
import com.grandchefsupreme.service.ShoppingListService;
import com.grandchefsupreme.utils.ApiResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-list")
@RequiredArgsConstructor
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    @PostMapping("/create")
    public ShoppingListDTO createShoppingList(
            HttpServletRequest request,
            @Valid @RequestParam Long userId,
            @Valid @RequestParam Long recipeId
    ) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Receta añadida a la lista de la compra"
        );

        return shoppingListService.addRecipeToShoppingList(userId, recipeId);
    }

    @GetMapping("/user/{userId}")
    public List<ShoppingListDTO> getUserLists(
            HttpServletRequest request,
            @Valid @PathVariable Long userId
    ) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Listas de la compra obtenidas correctamente"
        );

        return shoppingListService.getAllListsByUser(userId);
    }

    @PatchMapping("/{listId}/recipe/{recipeId}/ingredient/{ingredientId}/bought")
    public void markIngredientBought(
            HttpServletRequest request,
            @Valid @PathVariable Long listId,
            @PathVariable Long recipeId,
            @PathVariable Long ingredientId,
            @RequestParam boolean bought
    ) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                bought  ? "Ingrediente marcado como comprado"
                        : "Ingrediente marcado como no comprado"
        );

        shoppingListService.markIngredientBought(
                listId,
                recipeId,
                ingredientId,
                bought
        );
    }

    @DeleteMapping("/delete/boughtIngredients/{userId}")
    public void removeIngredientFromList(
            HttpServletRequest request,
            @Valid @PathVariable Long userId
    ) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Ingredientes comprados eliminados correctamente"
        );

        shoppingListService.removeBoughtIngredientsAndCleanList(userId);
    }
}