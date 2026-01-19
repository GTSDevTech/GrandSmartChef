package com.grandchefsupreme.mockito_services;

import com.grandchefsupreme.dto.IngredientDTO;
import com.grandchefsupreme.dto.RecipeDTO;
import com.grandchefsupreme.dto.RecipeStepDTO;
import com.grandchefsupreme.dto.TagDTO;
import com.grandchefsupreme.exceptions.BadRequestException;
import com.grandchefsupreme.model.Recipe;
import com.grandchefsupreme.repository.RecipeRepository;
import com.grandchefsupreme.service.RecipeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceIntegrationTest {
    @InjectMocks
    public RecipeService recipeService;

    @Mock
    public RecipeRepository recipeRepository;

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
        @DisplayName("Attributes Recipe(tags,ingredients,steps) - List Null or Empty")
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












}
