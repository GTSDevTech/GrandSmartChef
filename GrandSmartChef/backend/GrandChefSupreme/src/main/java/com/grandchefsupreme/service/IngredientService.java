package com.grandchefsupreme.service;

import com.grandchefsupreme.dto.IngredientCategoryDTO;
import com.grandchefsupreme.dto.IngredientDTO;
import com.grandchefsupreme.mapper.IngredientCategoryMapper;
import com.grandchefsupreme.mapper.IngredientMapper;
import com.grandchefsupreme.model.Ingredient;
import com.grandchefsupreme.model.IngredientCategory;
import com.grandchefsupreme.repository.IngredientCategoryRepository;
import com.grandchefsupreme.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientCategoryRepository ingredientCategoryRepository;
    private final IngredientCategoryMapper categoryMapper;
    private final IngredientMapper ingredientMapper;
    private final IngredientRepository ingredientRepository;

    @Cacheable("ingredientCategories")
    @Transactional(readOnly = true)
    public List<IngredientCategoryDTO> findAll() {
        List<IngredientCategory> ingredientCategories = ingredientCategoryRepository.findAll();
        if (ingredientCategories.isEmpty()) {

            return new ArrayList<>();
        }
        ingredientCategories.sort(
                (cat1, cat2) -> cat1.getName().compareToIgnoreCase(cat2.getName())
        );

        return categoryMapper.toDTO(ingredientCategories);
    }

    @Cacheable("ingredientByCategories")
    @Transactional(readOnly = true)
    public List<IngredientDTO> findIngredientsByCategory(Long idCategory) {
        List<Ingredient> ingredients = ingredientRepository.getAllByIngredientCategory(idCategory);
        return ingredientMapper.toDTO(ingredients);
    }




}
