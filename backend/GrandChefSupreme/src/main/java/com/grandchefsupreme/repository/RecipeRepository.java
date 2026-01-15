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
        SELECT r.*
        FROM public.recipe r
        JOIN recipe_tag rt ON r.id = rt.id_recipe
        JOIN client_tag ct ON ct.id_tag = rt.id_tag
        WHERE ct.id_client = :userId
          AND r.is_active = TRUE
        GROUP BY r.id
        HAVING COUNT(DISTINCT ct.id_tag) = (
            SELECT COUNT(*)
            FROM client_tag
            WHERE id_client = :userId
        )
        """,
            nativeQuery = true
    )
    List<Recipe> findByUserIdAndUserPreferences(@Param("userId") Long userId);


    @Query(value = """
        SELECT DISTINCT r.*
        FROM public.recipe r
        LEFT JOIN recipe_ingredient ri ON r.id = ri.id_recipe
        WHERE ri.id_ingredient IN (:ingredientIds)
          AND r.is_active = TRUE
        """,
            nativeQuery = true
    )
    List<Recipe> findByIngredientIds(@Param("ingredientIds") List<Long> ingredientIds);


    @Query(value = """
        SELECT DISTINCT r.*
        FROM public.recipe r
        JOIN public.recipe_tag rt ON r.id = rt.id_recipe
        JOIN public.client_tag ct ON ct.id_tag = rt.id_tag
        JOIN recipe_ingredient ri ON r.id = ri.id_recipe
        WHERE ct.id_client = :userId
          AND ri.id_ingredient IN (:ingredientIds)
          AND r.is_active = TRUE
        """,
            nativeQuery = true
    )
    List<Recipe> findByUserPreferencesAndIngredients(
            @Param("userId") Long userId,
            @Param("ingredientIds") List<Long> ingredientIds
    );
}


