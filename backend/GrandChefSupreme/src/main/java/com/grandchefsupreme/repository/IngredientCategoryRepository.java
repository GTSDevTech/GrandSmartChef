package com.grandchefsupreme.repository;

import com.grandchefsupreme.model.IngredientCategory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Long> {

    Optional<List<IngredientCategory>> findAllByOrderByNameAsc();
}
