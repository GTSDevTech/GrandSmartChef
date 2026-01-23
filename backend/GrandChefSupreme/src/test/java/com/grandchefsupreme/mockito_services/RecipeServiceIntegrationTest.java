package com.grandchefsupreme.mockito_services;

import com.grandchefsupreme.dto.*;
import com.grandchefsupreme.exceptions.BadRequestException;
import com.grandchefsupreme.mapper.IngredientCategoryMapper;
import com.grandchefsupreme.mapper.RecipeCardMapper;
import com.grandchefsupreme.mapper.RecipeDetailMapper;
import com.grandchefsupreme.model.Enums.Difficulty;
import com.grandchefsupreme.model.Enums.Unit;
import com.grandchefsupreme.model.Ingredient;
import com.grandchefsupreme.model.IngredientCategory;
import com.grandchefsupreme.model.Recipe;
import com.grandchefsupreme.model.RecipeStep;
import com.grandchefsupreme.repository.IngredientRepository;
import com.grandchefsupreme.repository.RecipeRatingRepository;
import com.grandchefsupreme.repository.RecipeRepository;
import com.grandchefsupreme.service.RecipeService;
import org.checkerframework.checker.units.qual.N;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceIntegrationTest {



    @InjectMocks
    public RecipeService recipeService;

    @Mock
    public RecipeRepository recipeRepository;

    @Mock
    public RecipeCardMapper recipeCardMapper;

    @Mock
    public RecipeDetailMapper recipeDetailMapper;

    @Mock
    public RecipeRatingRepository recipeRatingRepository;

    @Mock
    public IngredientRepository ingredientRepository;


    @Nested
    @DisplayName("Test de Integración -> Create Recipes")
    class createRecipeNegativeCases {


        @Test
        @DisplayName("Recipe null")
        void createRecipeNull(){
            //GIVEN
            //WHEN
            assertThrows(
                    BadRequestException.class,
                    () -> recipeService.createRecipe(null, null)
            );

            //THEN
            Mockito.verify(recipeRepository, Mockito.never()).saveAndFlush(Mockito.any(Recipe.class));

        }

        @Test
        @DisplayName("Attributes Recipe - List Null or Empty")
        void createRecipeWithoutData(){

            RecipeDTO recipeDTO = new RecipeDTO();
            recipeDTO.setTags(new HashSet<>(List.of(new TagDTO())));
            recipeDTO.setIngredients(null);
            recipeDTO.setSteps(new ArrayList<>(List.of(new RecipeStepDTO())));

            assertThrows(
                    BadRequestException.class,
                    () -> recipeService.createRecipe(recipeDTO, null)
            );

            Mockito.verify(recipeRepository, Mockito.never()).saveAndFlush(Mockito.any(Recipe.class));


        }


    }

    @Nested
    @DisplayName("Test de Integración -> Create Recipes")
    class createRecipePositiveCases{


        @Test
        @DisplayName("Create Recipe - Positive Case")
        void createRecipe(){

            TagDTO tagDTO = new TagDTO();
            tagDTO.setName("test");

            IngredientDTO ingredientDTO = new IngredientDTO();

            RecipeDTO recipeDTO = new RecipeDTO();
            recipeDTO.setTags(new HashSet<>(List.of(tagDTO)));



        }
    }

    @Nested
    @DisplayName("Find Recipe with Filters Successfully")
    class SearchFilteredRecipe{


        @Test
        @DisplayName("Recipe with Filters Successfully Return Recipe")
        void findRecipeWithoutFilters(){


            TagDTO tagDTO = new TagDTO();
            tagDTO.setName("testTag");

            RecipeCardDTO recipeCardDTO = new RecipeCardDTO();
            recipeCardDTO.setTags(new HashSet<>(List.of(tagDTO)));
            recipeCardDTO.setName("testRecipeName");
            recipeCardDTO.setDifficulty("Media");
            recipeCardDTO.setPrepTime(15.0);
            recipeCardDTO.setServings(2);

            Recipe createdRecipe = new Recipe();
            createdRecipe.setId(1L);

            //GIVEN
            Mockito.when(recipeRepository.findByIsActiveTrue()).thenReturn(List.of(new Recipe()));

            //THEN
            recipeService.searchRecipes(null, null);

            //WHEN
            Mockito.verify(recipeRepository, Mockito.times(1)).findByIsActiveTrue();
            Mockito.verify(recipeCardMapper, Mockito.times(1)).toDTO(Mockito.anyList());
            Mockito.verify(recipeRepository, Mockito.times(0)).findByUserIdAndUserPreferences(Mockito.anyLong());
            Mockito.verify(recipeRepository, Mockito.times(0)).findByIngredientIds(new ArrayList<>(Mockito.anyCollection()));
            Mockito.verify(recipeRepository, Mockito.times(0)).findByUserPreferencesAndIngredients(Mockito.anyLong(), new ArrayList<>(Mockito.anyCollection()));


        }

        @Test
        @DisplayName("Filter with User Preferences Successfully")
        void searchFilteredRecipeWithUserPreferences(){

            Mockito.when(recipeRepository.findByUserIdAndUserPreferences(Mockito.anyLong())).thenReturn(List.of(new Recipe()));
            Mockito.when(recipeCardMapper.toDTO(Mockito.anyList())).thenReturn(List.of(Mockito.mock(RecipeCardDTO.class)));

            //THEN
            recipeService.searchRecipes(1L, null);

            //WHEN

            Mockito.verify(recipeRepository, Mockito.times(1)).findByUserIdAndUserPreferences(Mockito.anyLong());
            Mockito.verify(recipeRepository, Mockito.times(0)).findByIsActiveTrue();
            Mockito.verify(recipeRepository, Mockito.times(0)).findByIngredientIds(new ArrayList<>(Mockito.anyCollection()));
            Mockito.verify(recipeRepository, Mockito.times(0)).findByUserPreferencesAndIngredients(Mockito.anyLong(), new ArrayList<>(Mockito.anyCollection()));
            Mockito.verify(recipeCardMapper, Mockito.times(1)).toDTO(Mockito.anyList());
            Mockito.verify(recipeRepository).findByUserIdAndUserPreferences(Mockito.anyLong());



        }
    }

    @Nested
    @DisplayName("Find Recipe With Details Successfully")
    class SearchFilteredRecipeWithDetailsSuccessfully{

        @Test
        @DisplayName("Search recipe by Id - Positive Case")
        void searchFilteredRecipeById(){

            RecipeDTO recipeDTO = new RecipeDTO();
            recipeDTO.setServings(3);

            IngredientDTO ingredientDTO = new IngredientDTO();
            ingredientDTO.setId(1L);

            RecipeIngredientDTO recipeIngredientDTO = new RecipeIngredientDTO();
            recipeIngredientDTO.setIngredient(ingredientDTO);
            recipeIngredientDTO.setQuantity(new BigDecimal("200"));
            recipeIngredientDTO.setUnit(Unit.GRAMO);

            recipeDTO.setIngredients(List.of(recipeIngredientDTO));

            Ingredient ingredient = new Ingredient();
            ingredient.setCalories(new BigDecimal("100"));
            ingredient.setProteins(new BigDecimal("10"));
            ingredient.setCarbs(new BigDecimal("20"));
            ingredient.setFats(new BigDecimal("5"));

            //GIVEN
            Mockito.when(recipeRepository.findByIdAndIsActiveTrue(Mockito.anyLong())).thenReturn(Optional.of(new Recipe()));
            Mockito.when(recipeDetailMapper.toDto(Mockito.any(Recipe.class))).thenReturn(recipeDTO);
            Mockito.when(recipeRatingRepository.findAverageRatingByRecipeId(Mockito.anyLong())).thenReturn(0.0);
            Mockito.when(ingredientRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ingredient));

            //THEN
            recipeService.getRecipeForDetails(1L);

            //WHEN

            Mockito.verify(recipeRepository, Mockito.times(1)).findByIdAndIsActiveTrue(Mockito.anyLong());
            Mockito.verify(recipeRepository).findByIdAndIsActiveTrue(Mockito.anyLong());
            Mockito.verify(recipeDetailMapper, Mockito.times(1)).toDto(Mockito.any(Recipe.class));
            Mockito.verify(recipeDetailMapper).toDto(Mockito.any(Recipe.class));
            Mockito.verify(recipeRatingRepository, Mockito.times(1)).findAverageRatingByRecipeId(Mockito.anyLong());
            Mockito.verify(recipeRatingRepository).findAverageRatingByRecipeId(Mockito.anyLong());

        }



    }


}
