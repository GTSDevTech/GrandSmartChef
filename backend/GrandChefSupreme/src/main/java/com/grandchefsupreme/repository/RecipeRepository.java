package com.grandchefsupreme.repository;

import com.grandchefsupreme.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;


@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("SELECT r FROM Recipe r WHERE r.isActive = true")
    List<Recipe> findByIsActiveTrue();


    Optional<Recipe> findByIdAndIsActiveTrue(Long id);


    @Query(value = """
        SELECT DISTINCT r.*
        FROM recipe r
        LEFT JOIN recipe_tag rt ON r.id = rt.id_recipe
        JOIN client_tag ct ON ct.id_tag = rt.id_tag
        LEFT JOIN client c ON ct.id_client = c.id
        WHERE ct.id_client = :userId
            AND ct.id_tag = rt.id_tag
            AND r.is_active = TRUE
    """, nativeQuery = true)
    List<Recipe> findByUserIdAndUserPreferences(@PathVariable("userId") Long userId);


    @Query(value = """
    select DISTINCT r.*  from recipe r
    left join recipe_ingredient ri on r.id = ri.id_recipe
    left join ingredient i on ri.id_ingredient = i.id
    where ri.id_ingredient = (:ingredientIds)
    """, nativeQuery = true)
    List<Recipe> findByIngredientIds(@Param("ingredientIds") List<Long> ingredientIds);

    @Query(value = """
    SELECT DISTINCT r.*
    FROM recipe r
    JOIN recipe_tag rt ON r.id = rt.id_recipe
    JOIN client_tag ct ON ct.id_tag = rt.id_tag
    JOIN recipe_ingredient ri ON r.id = ri.id_recipe
    WHERE ct.id_client = :userId
      AND ri.id_ingredient IN (:ingredientIds)
      AND r.is_active = TRUE
""", nativeQuery = true)
    List<Recipe> findByUserPreferencesAndIngredients(
            @Param("userId") Long userId,
            @Param("ingredientIds") List<Long> ingredientIds
    );



}





