package com.grandchefsupreme.mockito_services;

import com.grandchefsupreme.dto.ClientDTO;
import com.grandchefsupreme.dto.TopClientDTO;
import com.grandchefsupreme.dto.TopIngredientsDTO;
import com.grandchefsupreme.repository.ClientRepository;
import com.grandchefsupreme.repository.IngredientRepository;
import com.grandchefsupreme.repository.RecipeRepository;
import com.grandchefsupreme.service.StatisticService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class StatisticsServiceIntegrationTest {


    @InjectMocks
    public StatisticService statisticsService;

    @Mock
    public IngredientRepository ingredientRepository;

    @Mock
    public ClientRepository clientRepository;



    @Nested
    @DisplayName("Calculate Statistics of Recipes and Users Successfully")
    class StatisticsOfRecipesAndUsersSuccessfully {


        @Test
        @DisplayName("Search History of Recipes - Positive Case")
        void statisticsTop5Ingredients() {

            //GIVEN
            Mockito.when(ingredientRepository.findTop5IngredientsByRecipeCount()).thenReturn(List.of(new TopIngredientsDTO("Calabaza", 1L)));


            //THEN

            statisticsService.getTop5Ingredients();

            //WHEN

            Mockito.verify(ingredientRepository).findTop5IngredientsByRecipeCount();
            Mockito.verify(ingredientRepository, Mockito.times(1)).findTop5IngredientsByRecipeCount();
            Mockito.verifyNoMoreInteractions(ingredientRepository);


        }



        @Test
        @DisplayName("Search Users With Same Recipes - Positive Case")
        void statisticsTopClient() {

            //GIVEN
            Mockito.when(clientRepository.findClientByTopRecipes()).thenReturn(List.of(new TopClientDTO(1L, "GTS", "gts@gmail.com")));

            //THEN
            statisticsService.getTopRecipes();


            //WHEN
            Mockito.verify(clientRepository, Mockito.times(1)).findClientByTopRecipes();
            Mockito.verify(clientRepository).findClientByTopRecipes();

        }

    }

    @Nested
    @DisplayName("Calculate Statistics of Recipes and Users - Negative Cases")
    class StatisticsOfRecipesAndUsersWithoutData {


        @Test
        @DisplayName("Search History of Recipes With Empty List - Negative Case")
        void statisticsTop5IngredientsWithEmptyList() {

            Mockito.when(ingredientRepository.findTop5IngredientsByRecipeCount()).thenReturn(Collections.emptyList());

            statisticsService.getTop5Ingredients();

            Mockito.verify(ingredientRepository, Mockito.times(1)).findTop5IngredientsByRecipeCount();
            Mockito.verifyNoMoreInteractions(ingredientRepository);

        }



        @Test
        @DisplayName("Search Users With Same Recipes - Positive Case")
        void statisticsTopClient() {

            Mockito.when(clientRepository.findClientByTopRecipes()).thenReturn(Collections.emptyList());

            statisticsService.getTopRecipes();

            Mockito.verify(clientRepository, Mockito.times(1)).findClientByTopRecipes();
            Mockito.verifyNoMoreInteractions(clientRepository);
        }

    }



}
