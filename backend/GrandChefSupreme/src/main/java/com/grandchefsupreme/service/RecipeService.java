package com.grandchefsupreme.service;

import com.grandchefsupreme.dto.*;
import com.grandchefsupreme.exceptions.BadRequestException;
import com.grandchefsupreme.exceptions.NotFoundException;
import com.grandchefsupreme.mapper.RecipeCardMapper;
import com.grandchefsupreme.mapper.RecipeDetailMapper;
import com.grandchefsupreme.model.*;
import com.grandchefsupreme.model.Enums.Difficulty;
import com.grandchefsupreme.repository.IngredientRepository;
import com.grandchefsupreme.repository.RecipeRatingRepository;
import com.grandchefsupreme.repository.RecipeRepository;
import com.grandchefsupreme.repository.TagRepository;
import com.grandchefsupreme.utils.FileStorageUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeRatingRepository recipeRatingRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeCardMapper recipeCardMapper;
    private final RecipeDetailMapper recipeDetailMapper;
    private final TagRepository tagRepository;
    private final FileStorageUtil fileStorageUtil;


    @Transactional(readOnly = true)
    public List<RecipeCardDTO> getAllActiveRecipesForCards() {

        List<Recipe> recipes = recipeRepository.findByIsActiveTrue();

        return recipes.stream()
                .map(recipe -> {
                    RecipeCardDTO dto = recipeCardMapper.toDTO(recipe);
                    Double avg = recipeRatingRepository.findAverageRatingByRecipeId(recipe.getId());
                    dto.setAverageRating(avg != null ? avg : 0.0);
                    return dto;
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RecipeCardDTO> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream()
                .map(recipe -> {
                    RecipeCardDTO dto = recipeCardMapper.toDTO(recipe);
                    dto.setAverageRating(
                            recipeRatingRepository.findAverageRatingByRecipeId(recipe.getId())
                    );
                    return dto;
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public RecipeDTO getRecipeForDetails(Long id) {
        Recipe recipe = recipeRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new NotFoundException("Receta no encontrada"));

        RecipeDTO recipeDTO = recipeDetailMapper.toDto(recipe);

        Double avg = recipeRatingRepository.findAverageRatingByRecipeId(recipe.getId());

        if (avg == null) {
            avg = 0.0;
        }

        if (avg.isNaN() || avg.isInfinite() || avg < 0 || avg > 5) {
            throw new IllegalArgumentException(
                    "Valor de media inválido o corrupto: " + avg
            );
        }
        recipeDTO.setAverageRating(avg);
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
    public RecipeDTO createRecipe(RecipeDTO recipeDTO, MultipartFile photoFile) throws IOException {

        if (recipeDTO == null) {
            throw new BadRequestException("Los datos de la receta son obligatorios");
        }

        if (recipeDTO.getIngredients() == null || recipeDTO.getIngredients().isEmpty()) {
            throw new BadRequestException("La receta debe tener al menos un ingrediente");
        }

        if (recipeDTO.getSteps() == null || recipeDTO.getSteps().isEmpty()) {
            throw new BadRequestException("La receta debe tener al menos un paso");
        }

        if(recipeDTO.getAverageRating() == null){
            recipeDTO.setAverageRating(0.0);
        }


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

        if (photoFile != null && !photoFile.isEmpty()) {
            String photoPath = fileStorageUtil.saveProfilePhoto(photoFile);
            recipe.setImageUrl(photoPath);
        } else if (recipe.getImageUrl() == null || recipe.getImageUrl().isEmpty()) {
            String DEFAULT_PHOTO = "uploads/profile/default_profile_image.png";
            recipe.setImageUrl(DEFAULT_PHOTO);
        }

        Recipe savedRecipe = recipeRepository.saveAndFlush(recipe);
        return recipeDetailMapper.toDto(savedRecipe);
    }

    @Transactional
    public RecipeDTO updateRecipe(RecipeDTO recipeDTO, MultipartFile photoFile) throws IOException {

        Recipe existingRecipe = recipeRepository.findById(recipeDTO.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Recipe not found with id " + recipeDTO.getId()));


        existingRecipe.setName(recipeDTO.getName());
        existingRecipe.setDifficulty(Difficulty.valueOf(recipeDTO.getDifficulty()));
        existingRecipe.setServings(recipeDTO.getServings());
        existingRecipe.setPrepTime(recipeDTO.getPrepTime());
        existingRecipe.setDescription(recipeDTO.getDescription());


        existingRecipe.getRecipeIngredients().clear();

        if (recipeDTO.getIngredients() != null) {
            for (RecipeIngredientDTO riDto : recipeDTO.getIngredients()) {

                Ingredient ingredient = ingredientRepository.findById(
                        riDto.getIngredient().getId()
                ).orElseThrow(() ->
                        new EntityNotFoundException(
                                "Ingredient not found with id " + riDto.getIngredient().getId()
                        ));

                RecipeIngredient ri = new RecipeIngredient();
                ri.setQuantity(riDto.getQuantity());
                ri.setUnit(riDto.getUnit());
                ri.setIngredient(ingredient);
                ri.setRecipe(existingRecipe);

                existingRecipe.getRecipeIngredients().add(ri);
            }
        }

        existingRecipe.getRecipeSteps().clear();

        if (recipeDTO.getSteps() != null) {
            for (RecipeStepDTO stepDto : recipeDTO.getSteps()) {

                RecipeStep step = new RecipeStep();
                step.setStepNumber(stepDto.getStepNumber());
                step.setInstruction(stepDto.getInstruction());
                step.setRecipe(existingRecipe);

                existingRecipe.getRecipeSteps().add(step);
            }
        }

        if (recipeDTO.getTags() != null) {
            Set<Tag> resolvedTags = new HashSet<>();

            for (TagDTO tagDTO : recipeDTO.getTags()) {

                Tag tag = (tagDTO.getId() != null)
                        ? tagRepository.findById(tagDTO.getId())
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Tag not found with id " + tagDTO.getId()
                                ))
                        : tagRepository.findByNameIgnoreCase(tagDTO.getName())
                        .orElseGet(() -> {
                            Tag newTag = new Tag();
                            newTag.setName(tagDTO.getName());
                            return tagRepository.save(newTag);
                        });

                resolvedTags.add(tag);
            }

            existingRecipe.setTags(resolvedTags);
        }

        if (photoFile != null && !photoFile.isEmpty()) {
            String photoPath = fileStorageUtil.saveProfilePhoto(photoFile);
            existingRecipe.setImageUrl(photoPath);
        } else if (existingRecipe.getImageUrl() == null || existingRecipe.getImageUrl().isEmpty()) {
            String DEFAULT_PHOTO = "uploads/profile/default_profile_image.png";
            existingRecipe.setImageUrl(DEFAULT_PHOTO);
        }

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
