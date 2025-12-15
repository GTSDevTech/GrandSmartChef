package com.grandchefsupreme.service;

import com.grandchefsupreme.dto.*;
import com.grandchefsupreme.exceptions.NotFoundException;
import com.grandchefsupreme.mapper.RecipeCardMapper;
import com.grandchefsupreme.mapper.RecipeDetailMapper;
import com.grandchefsupreme.model.*;
import com.grandchefsupreme.model.Enums.Difficulty;
import com.grandchefsupreme.repository.IngredientRepository;
import com.grandchefsupreme.repository.RecipeRepository;
import com.grandchefsupreme.repository.TagRepository;
import com.grandchefsupreme.utils.FileStorageUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeCardMapper recipeCardMapper;
    private final RecipeDetailMapper recipeDetailMapper;
    private final TagRepository tagRepository;
    private final FileStorageUtil fileStorageUtil;


    @Transactional(readOnly = true)
    public List<RecipeCardDTO> getAllActiveRecipesForCards() {
        List<Recipe> recipes = recipeRepository.findByIsActiveTrue();
        return recipeCardMapper.toDTO(recipes);
    }


    @Transactional(readOnly = true)
    public List<RecipeCardDTO> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipeCardMapper.toDTO(recipes);
    }

    @Transactional(readOnly = true)
    public RecipeDTO getRecipeForDetails(Long id) {
        Recipe recipe = recipeRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new NotFoundException("Receta no encontrada"));
        if (recipe == null) {
            return null;
        }
        RecipeDTO recipeDTO = recipeDetailMapper.toDto(recipe);

        recipeDTO.setNutritionInfo(getNutritionInfo(recipeDTO.getIngredients(), recipeDTO.getServings()));

        return recipeDTO;
    }

    private NutritionInfoDTO getNutritionInfo(List<RecipeIngredientDTO> ingredients, int servings) {
        NutritionInfoDTO nutritionInfo = new NutritionInfoDTO();
        BigDecimal totalCalories = BigDecimal.ZERO;
        BigDecimal totalProteins = BigDecimal.ZERO;
        BigDecimal totalCarbs = BigDecimal.ZERO;
        BigDecimal totalFats = BigDecimal.ZERO;

        for (RecipeIngredientDTO ingr : ingredients) {
            if (ingr == null || ingr.getQuantity() == null) continue;

            Ingredient ingredient = ingredientRepository.findById(ingr.getIngredient().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Ingrediente no encontrado"));

            BigDecimal factor = ingr.getQuantity().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            totalCalories = totalCalories.add(ingredient.getCalories().multiply(factor));
            totalProteins = totalProteins.add(ingredient.getProteins().multiply(factor));
            totalCarbs = totalCarbs.add(ingredient.getCarbs().multiply(factor));
            totalFats = totalFats.add(ingredient.getFats().multiply(factor));

        }


        BigDecimal totalServings = BigDecimal.valueOf(servings);
        nutritionInfo.setCalories(totalCalories.divide(totalServings, 2, RoundingMode.HALF_UP));
        nutritionInfo.setProteins(totalProteins.divide(totalServings, 2, RoundingMode.HALF_UP));
        nutritionInfo.setCarbs(totalCarbs.divide(totalServings, 2, RoundingMode.HALF_UP));
        nutritionInfo.setFats(totalFats.divide(totalServings, 2, RoundingMode.HALF_UP));

        return nutritionInfo;
    }


    @Transactional
    public RecipeDTO createRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = recipeDetailMapper.toEntity(recipeDTO);
        recipe.setIsActive(true);

        if (recipe.getRecipeIngredients() != null) {
            for (RecipeIngredient ri : recipe.getRecipeIngredients()) {

                Long ingredientId = ri.getIngredient().getId();

                Ingredient ingredient = ingredientRepository.findById(ingredientId)
                        .orElseThrow(() ->
                                new EntityNotFoundException("Ingrediente no encontrado con id " + ingredientId));

                ri.setIngredient(ingredient);
                ri.setRecipe(recipe);
            }
        }

        if (recipeDTO.getSteps() != null && !recipeDTO.getSteps().isEmpty()) {
            for (RecipeStep step : recipe.getRecipeSteps()) {
                step.setRecipe(recipe);
            }
        }

        if (recipe.getTags() != null && !recipe.getTags().isEmpty()) {

            Set<Tag> resolvedTags = new HashSet<>();

            for (Tag tag : recipe.getTags()) {
                Tag resolved = tagRepository
                        .findByNameIgnoreCase(tag.getName())
                        .orElseGet(() -> tagRepository.save(tag));

                resolvedTags.add(resolved);
            }

            recipe.setTags(resolvedTags);
        }

        Recipe savedRecipe = recipeRepository.saveAndFlush(recipe);
        return recipeDetailMapper.toDto(savedRecipe);
    }

//            Recipe newRecipe = new Recipe();
//            newRecipe.setIsActive(true);
//            newRecipe.setName(recipeDTO.getName());
//            newRecipe.setDifficulty(Difficulty.valueOf(recipeDTO.getDifficulty()));
//            newRecipe.setServings(recipeDTO.getServings());
//            newRecipe.setPrepTime(recipeDTO.getPrepTime());
//            newRecipe.setDescription(recipeDTO.getDescription());
//            newRecipe.setImageUrl(recipeDTO.getImageUrl());
//            if (recipeDTO.getIngredients() != null && !recipeDTO.getIngredients().isEmpty()) {
//                for (RecipeIngredientDTO ri : recipeDTO.getIngredients()) {
//                    Ingredient ingredient = ingredientRepository.findById(ri.getIngredientId())
//                            .orElseThrow(() -> new EntityNotFoundException("Ingrediente no encontrado"));
//                    RecipeIngredient recipeIngredient = new RecipeIngredient();
//                    recipeIngredient.setRecipe(newRecipe);
//                    recipeIngredient.setIngredient(ingredient); // Usamos el ingrediente ya existente
//                    recipeIngredient.setQuantity(ri.getQuantity()); // Asignamos la cantidad
//                    recipeIngredient.setUnit(ri.getUnit());
//
//                    newRecipe.getRecipeIngredients().add(recipeIngredient);
//                }
//
//            }
//
//            if (recipeDTO.getSteps() != null && !recipeDTO.getSteps().isEmpty()) {
//                for (RecipeStepDTO stepDTO : recipeDTO.getSteps()) {
//                    RecipeStep step = new RecipeStep();
//                    step.setRecipe(newRecipe);
//                    step.setStepNumber(stepDTO.getStepNumber());
//                    step.setInstruction(stepDTO.getInstruction());
//                    newRecipe.getRecipeSteps().add(step);
//
//                }
//            }
//
//            if (recipeDTO.getTags() != null && !recipeDTO.getTags().isEmpty()) {
//                Set<Tag> tags = new HashSet<>();
//                for (TagDTO tagDTO : recipeDTO.getTags()) {
//                    Tag tag = tagRepository.findByName(tagDTO.getName())
//                            .orElseGet(() -> {
//                                Tag newTag = new Tag();
//                                newTag.setName(tagDTO.getName());
//                                return tagRepository.save(newTag);
//                            });
//                    tags.add(tag);
//                }
//                newRecipe.setTags(tags);
//            }
//
//                Recipe savedRecipe = recipeRepository.saveAndFlush(newRecipe);
//
//
//                return recipeDetailMapper.toDto(savedRecipe);



    @Transactional
    public RecipeDTO updateRecipe(RecipeDTO recipeDTO) {
        // Buscar la receta existente por su ID
        Recipe existingRecipe = recipeRepository.findById(recipeDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found with id " + recipeDTO.getId()));

        // Actualizar los campos de la receta existente con los datos del DTO
        recipeDetailMapper.updateRecipeFromDto(recipeDTO, existingRecipe);

        // Actualizar ingredientes
        if (existingRecipe.getRecipeIngredients() != null) {
            for (RecipeIngredient ri : existingRecipe.getRecipeIngredients()) {
                Long ingredientId = ri.getIngredient().getId();
                Ingredient ingredient = ingredientRepository.findById(ingredientId)
                        .orElseThrow(() -> new EntityNotFoundException("Ingredient not found with id " + ingredientId));
                ri.setIngredient(ingredient);
                ri.setRecipe(existingRecipe);
            }
        }

        // Actualizar pasos de la receta
        if (recipeDTO.getSteps() != null && !recipeDTO.getSteps().isEmpty()) {
            for (RecipeStep step : existingRecipe.getRecipeSteps()) {
                step.setRecipe(existingRecipe);
            }
        }

        if (recipeDTO.getTags() != null && !recipeDTO.getTags().isEmpty()) {
            Set<Tag> resolvedTags = new HashSet<>();

            for (TagDTO tagDTO : recipeDTO.getTags()) {
                // Buscar la etiqueta por nombre
                Tag resolvedTag = tagRepository.findByNameIgnoreCase(tagDTO.getName())
                        .orElseGet(() -> {
                            // Si no existe, crear una nueva etiqueta
                            Tag newTag = new Tag();
                            newTag.setName(tagDTO.getName());
                            return tagRepository.save(newTag);
                        });

                resolvedTags.add(resolvedTag);
            }

            // Actualizar las etiquetas de la receta
            existingRecipe.setTags(resolvedTags);
        }


        if (recipeDTO.getImageUrl() != null && !recipeDTO.getImageUrl().isBlank()) {
            existingRecipe.setImageUrl(recipeDTO.getImageUrl());  // Actualiza la URL de la imagen
        }

        // Guardar y devolver la receta actualizada
        Recipe updatedRecipe = recipeRepository.saveAndFlush(existingRecipe);
        return recipeDetailMapper.toDto(updatedRecipe);
    }




    @Transactional
    public void deleteRecipe(Long id){
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found with id " + id));
        recipeRepository.deleteById(existingRecipe.getId());
    }


    @Transactional(readOnly = true)
    public List<RecipeCardDTO> searchRecipes(
            Long userId,
            List<Long> ingredientIds
    ) {

        boolean hasUser = userId != null;
        boolean hasIngredients = ingredientIds != null && !ingredientIds.isEmpty();


        if (!hasUser && !hasIngredients) {
            return recipeCardMapper.toDTO(
                    recipeRepository.findByIsActiveTrue()
            );
        }
        if (hasUser && !hasIngredients) {
            return recipeCardMapper.toDTO(
                    recipeRepository.findByUserIdAndUserPreferences(userId)
            );
        }

        if (!hasUser) {
            return recipeCardMapper.toDTO(
                    recipeRepository.findByIngredientIds(ingredientIds)
            );
        }
        List<Recipe> recipes =
                recipeRepository.findByUserPreferencesAndIngredients(
                        userId,
                        ingredientIds
                );

        return recipeCardMapper.toDTO(recipes);
    }


}
