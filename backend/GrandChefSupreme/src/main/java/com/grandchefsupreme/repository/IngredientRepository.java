package com.grandchefsupreme.repository;

import com.grandchefsupreme.dto.TopIngredientsDTO;
import com.grandchefsupreme.model.Ingredient;
import com.grandchefsupreme.model.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

    @Repository
    public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

        List<Ingredient> getAllByRecipeIngredients(List<RecipeIngredient> recipeIngredients);


        @Query("SELECT i FROM Ingredient i WHERE i.ingredientCategory.id = :idCategory")
        List<Ingredient> getAllByIngredientCategory(@Param("idCategory") Long idCategory);

        @Query(value = """
        select i.name , count(DISTINCT ri.id_recipe) as recipes_count from public.ingredient i
        join recipe_ingredient ri on i.id = ri.id_ingredient
        group by i.name
        order by recipes_count desc
        limit 5;
    """, nativeQuery = true)
        List<TopIngredientsDTO> findTop5IngredientsByRecipeCount();
    }
