package com.grandchefsupreme.repository;

import com.grandchefsupreme.model.RecipeStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {
}
