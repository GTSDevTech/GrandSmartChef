package com.grandchefsupreme.service;

import com.grandchefsupreme.dto.ShoppingListDTO;
import com.grandchefsupreme.exceptions.BadRequestException;
import com.grandchefsupreme.exceptions.NotFoundException;
import com.grandchefsupreme.mapper.ShoppingListMapper;
import com.grandchefsupreme.model.*;
import com.grandchefsupreme.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final ClientRepository clientRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final ShoppingListIngredientRepository shoppingListIngredientRepository;
    private final ShoppingListMapper shoppingListMapper;


    @Transactional
    public ShoppingListDTO addRecipeToShoppingList(Long userId, Long recipeId) {

        if (userId == null || recipeId == null) {
            throw new BadRequestException("La receta no puede ser nula");
        }

        boolean shoppingListExist = shoppingListRepository.existsItemWithRecipeInActiveShoppingList(recipeId);

        if (shoppingListExist) {
            throw new BadRequestException("La receta ya está activa en la lista de la compra");
        }

        Client client = clientRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NotFoundException("Receta no encontrada"));


        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setClient(client);
        shoppingList.setStatus(false);
        shoppingList = shoppingListRepository.save(shoppingList);

        List<RecipeIngredient> recipeIngredients = recipeIngredientRepository.findAllByRecipeId(recipeId);

        for (RecipeIngredient recipeIng : recipeIngredients) {
            ShoppingListIngredient sli = new ShoppingListIngredient();
            sli.setShoppingList(shoppingList);
            sli.setRecipe(recipe);
            sli.setIngredient(recipeIng.getIngredient());
            sli.setQuantity(recipeIng.getQuantity());
            sli.setUnit(recipeIng.getUnit());
            sli.setBought(false);
            shoppingListIngredientRepository.save(sli);
            shoppingList.getItems().add(sli);
        }

        shoppingList = shoppingListRepository.findByIdWithItems(shoppingList.getId())
                .orElseThrow(() -> new NotFoundException("Lista no encontrada"));


        return shoppingListMapper.toDTO(shoppingList);
    }

    @Transactional
    public List<ShoppingListDTO> getAllListsByUser(Long userId) {
        List<ShoppingList> shoppingLists = shoppingListRepository.findAllByClientId(userId);

        return shoppingListMapper.toDTO(shoppingLists);

    }

    @Transactional
    public void markIngredientBought(Long listId, Long recipeId, Long ingredientId, Boolean bought) {
        ShoppingListIngredient sli = shoppingListIngredientRepository
                .findByShoppingListIdAndRecipeIdAndIngredientId(listId, recipeId, ingredientId)
                        .orElseThrow(() -> new NotFoundException("Ingrediente no encontrado en la lista de la compra"));

        sli.setBought(bought);
    }

    @Transactional
    public void removeBoughtIngredientsAndCleanList(Long userId) {
        shoppingListIngredientRepository.deleteAllBoughtByUserId(userId);
        shoppingListRepository.deleteAllEmptyListsByUserId(userId);

    }
}
