package com.grandchefsupreme.controller;

import com.grandchefsupreme.dto.ShoppingListDTO;
import com.grandchefsupreme.service.ShoppingListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-list")
@RequiredArgsConstructor
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    @PostMapping("/create")
    public ResponseEntity<ShoppingListDTO> createShoppingList(@Valid @RequestParam Long userId, @RequestParam Long recipeId) {
        ShoppingListDTO shoppingList = shoppingListService.addRecipeToShoppingList(userId, recipeId);
        return ResponseEntity.ok(shoppingList);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ShoppingListDTO>> getUserLists(@Valid @PathVariable Long userId) {
        List<ShoppingListDTO> lists = shoppingListService.getAllListsByUser(userId);
        return ResponseEntity.ok(lists);
    }

    @PatchMapping("/{listId}/recipe/{recipeId}/ingredient/{ingredientId}/bought")
    public ResponseEntity<Void> markIngredientBought(@Valid
            @PathVariable Long listId,
            @PathVariable Long recipeId,
            @PathVariable Long ingredientId,
            @RequestParam boolean bought) {

        shoppingListService.markIngredientBought(listId, recipeId, ingredientId, bought);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/boughtIngredients/{userId}")
    public ResponseEntity<Void> removeIngredientFromList(@Valid
            @PathVariable Long userId) {
        shoppingListService.removeBoughtIngredientsAndCleanList(userId);
        return ResponseEntity.noContent().build();
    }

}
