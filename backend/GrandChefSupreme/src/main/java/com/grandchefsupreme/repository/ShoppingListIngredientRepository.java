package com.grandchefsupreme.repository;

import com.grandchefsupreme.model.ShoppingListIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingListIngredientRepository extends JpaRepository<ShoppingListIngredient, Long> {


    List<ShoppingListIngredient> findAllByShoppingListId(Long shoppingListId);

    Optional<ShoppingListIngredient> findByShoppingListIdAndRecipeIdAndIngredientId(Long shoppingListId, Long recipeId, Long ingredientId);


    List<ShoppingListIngredient> findAllByShoppingListIdAndBought(Long listId, boolean b);



    long countByShoppingListId(Long listId);

     @Modifying
     @Query("DELETE FROM ShoppingListIngredient sli " +
             "WHERE sli.shoppingList.client.id = :userId " +
             "AND sli.bought= true")
     void deleteAllBoughtByUserId(Long userId);
}