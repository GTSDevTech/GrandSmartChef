package com.grandchefsupreme.repository;

import com.grandchefsupreme.model.RecipeRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRatingRepository extends JpaRepository<RecipeRating, Long> {

    Optional<RecipeRating> findByRecipe_IdAndClient_Id(Long recipeId, Long clientId);


    @Query("""
        SELECT AVG(r.rating)
        FROM RecipeRating r
        WHERE r.recipe.id = :recipeId
    """)
    Double findAverageRatingByRecipeId(@Param("recipeId") Long recipeId);
}
