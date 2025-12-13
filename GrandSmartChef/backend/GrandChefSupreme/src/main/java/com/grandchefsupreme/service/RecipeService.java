package com.grandchefsupreme.service;

import com.grandchefsupreme.dto.NutritionInfoDTO;
import com.grandchefsupreme.dto.RecipeCardDTO;
import com.grandchefsupreme.dto.RecipeDTO;
import com.grandchefsupreme.dto.RecipeIngredientDTO;
import com.grandchefsupreme.exceptions.NotFoundException;
import com.grandchefsupreme.mapper.RecipeCardMapper;
import com.grandchefsupreme.mapper.RecipeDetailMapper;
import com.grandchefsupreme.model.Recipe;
import com.grandchefsupreme.model.RecipeIngredient;
import com.grandchefsupreme.model.RecipeStep;
import com.grandchefsupreme.repository.RecipeRepository;
import com.grandchefsupreme.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCardMapper recipeCardMapper;
    private final RecipeDetailMapper recipeDetailMapper;
    private final TagRepository tagRepository;

    @Cacheable("allRecipes")
    @Transactional(readOnly = true)
    public List<RecipeCardDTO> getAllActiveRecipesForCards(){
        List<Recipe> recipes = recipeRepository.findByIsActiveTrue();
        return recipeCardMapper.toDTO(recipes);
    }


    @Transactional(readOnly = true)
    public List<RecipeCardDTO> getAllRecipes(){
        List<Recipe> recipes = recipeRepository.findAll();
        return recipeCardMapper.toDTO(recipes);
    }

    @Transactional(readOnly = true)
    public RecipeDTO getRecipeForDetails(Long id){
        Recipe recipe = recipeRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new NotFoundException("Recipe not found"));
        if (recipe == null) {
            return null;
        }
        RecipeDTO recipeDTO = recipeDetailMapper.toDto(recipe);

        recipeDTO.setNutritionInfo(getNutritionInfo(recipeDTO.getIngredients(), recipeDTO.getServings()));

        return recipeDTO;
    }

    private NutritionInfoDTO getNutritionInfo(List<RecipeIngredientDTO> ingredients, int servings){
        NutritionInfoDTO nutritionInfo = new NutritionInfoDTO();
        BigDecimal totalCalories = BigDecimal.ZERO;
        BigDecimal totalProteins = BigDecimal.ZERO;
        BigDecimal totalCarbs = BigDecimal.ZERO;
        BigDecimal totalFats = BigDecimal.ZERO;

        for (RecipeIngredientDTO ingr : ingredients) {
            if (ingr == null || ingr.getQuantity() == null) continue;

            BigDecimal factor = ingr.getQuantity().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            totalCalories = totalCalories.add(ingr.getIngredient().getCalories().multiply(factor));
            totalProteins = totalProteins.add(ingr.getIngredient().getProteins().multiply(factor));
            totalCarbs = totalCarbs.add(ingr.getIngredient().getCarbs().multiply(factor));
            totalFats = totalFats.add(ingr.getIngredient().getFats());

            BigDecimal totalServings = BigDecimal.valueOf(servings);
            nutritionInfo.setCalories(totalCalories.divide(totalServings, 2, RoundingMode.HALF_UP));
            nutritionInfo.setProteins(totalProteins.divide(totalServings, 2, RoundingMode.HALF_UP));
            nutritionInfo.setCarbs(totalCarbs.divide(totalServings, 2, RoundingMode.HALF_UP));
            nutritionInfo.setFats(totalFats.divide(totalServings, 2, RoundingMode.HALF_UP));

        }
        return nutritionInfo;
    }

    @Transactional
    public RecipeDTO createRecipe(RecipeDTO recipeDTO){
        Recipe recipe = recipeDetailMapper.toEntity(recipeDTO);
        recipe.setIsActive(true);

        if (recipeDTO.getIngredients() != null && !recipeDTO.getIngredients().isEmpty()) {
            for (RecipeIngredient ri : recipe.getRecipeIngredients()) {
                ri.setRecipe(recipe);
            }
        }

        if (recipeDTO.getSteps() != null && !recipeDTO.getSteps().isEmpty()) {
            for (RecipeStep step : recipe.getRecipeSteps()) {
                step.setRecipe(recipe);
            }
        }

        if (recipe.getTags() != null && !recipe.getTags().isEmpty()) {
            recipe.setTags(recipe.getTags());
        }
        Recipe savedRecipe = recipeRepository.saveAndFlush(recipe);
        return recipeDetailMapper.toDto(savedRecipe);
    }

    @Transactional
    public RecipeDTO updateRecipe(RecipeDTO recipeDTO){

        Recipe existingRecipe = recipeRepository.findById(recipeDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found with id " + recipeDTO.getId()));
        recipeDetailMapper.updateRecipeFromDto(recipeDTO, existingRecipe);
        Recipe updatedRecipe = recipeRepository.saveAndFlush(existingRecipe);
        return recipeDetailMapper.toDto(updatedRecipe);

    }

    @Transactional
    public void deleteRecipe(Long id){
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found with id " + id));
        recipeRepository.deleteById(existingRecipe.getId());
    }

    public List<RecipeCardDTO>getByUserPreferences(Long userId) {
        List<Recipe> recipesWithPreferences = recipeRepository.findByUserIdAndUserPreferences(userId);
        if(recipesWithPreferences.isEmpty()) {
            List<Recipe> recipes = recipeRepository.findByIsActiveTrue();
            return recipeCardMapper.toDTO(recipes);
        }
        return recipeCardMapper.toDTO(recipesWithPreferences);

    }

    public List<RecipeCardDTO> getByIngredientIds(List<Long> ingredientIds) {
        List<Recipe> recipeswithIngredients = recipeRepository.findByIngredientIds(ingredientIds);
        if(recipeswithIngredients.isEmpty()) {
            List<Recipe> recipes = recipeRepository.findByIsActiveTrue();
            return recipeCardMapper.toDTO(recipes);
        }
        List<Recipe> recipes = recipeRepository.findByIngredientIds(ingredientIds);
        return recipeCardMapper.toDTO(recipes);
    }


}
