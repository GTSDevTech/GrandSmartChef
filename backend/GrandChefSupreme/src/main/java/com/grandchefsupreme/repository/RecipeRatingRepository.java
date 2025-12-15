package com.grandchefsupreme.repository;

import com.grandchefsupreme.model.RecipeRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRatingRepository extends JpaRepository<RecipeRating, Long> {
}
