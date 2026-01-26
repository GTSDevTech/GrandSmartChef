package com.grandchefsupreme.mockito_services;

import com.grandchefsupreme.dto.HistoryDTO;
import com.grandchefsupreme.dto.RecipeCardDTO;
import com.grandchefsupreme.exceptions.BadRequestException;
import com.grandchefsupreme.mapper.HistoryMapper;
import com.grandchefsupreme.model.History;
import com.grandchefsupreme.model.Recipe;
import com.grandchefsupreme.repository.HistoryRepository;
import com.grandchefsupreme.repository.RecipeRatingRepository;
import com.grandchefsupreme.service.HistoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class HistoryServiceIntegrationTest {

    @InjectMocks
    public HistoryService historyService;

    @Mock
    public HistoryRepository historyRepository;

    @Mock
    public HistoryMapper historyMapper;

    @Mock
    public RecipeRatingRepository recipeRatingRepository;


    @Nested
    @DisplayName("Create History of Recipes Successfully")
    class AddRecipeToCollectionSuccessfully{

        @Test
        @DisplayName("Create History of Recipes - Positive Case")
        void createHistoryOfRecipesPositiveCase() {

            RecipeCardDTO recipeCardDTO = new RecipeCardDTO();
            recipeCardDTO.setId(1L);

            HistoryDTO historyDTO =  new HistoryDTO();
            historyDTO.setId(1L);
            historyDTO.setClientId(1L);
            historyDTO.setRecipe(recipeCardDTO);
            historyDTO.setDate("2024-06-01");


            //GIVEN

            Mockito.when(historyMapper.toEntity(Mockito.any(HistoryDTO.class))).thenReturn(Mockito.mock(History.class));
            Mockito.when(historyRepository.save(Mockito.any(History.class))).thenReturn(Mockito.mock(History.class));
            Mockito.when(historyMapper.toDTO(Mockito.any(History.class))).thenReturn(historyDTO);

            //THEN
            historyService.createHistory(historyDTO);

            //WHEN
            Mockito.verify(historyRepository).save(Mockito.any(History.class));
            Mockito.verify(historyMapper).toEntity(Mockito.any(HistoryDTO.class));
            Mockito.verify(historyMapper).toDTO(Mockito.any(History.class));
            Mockito.verify(historyRepository, Mockito.times(1)).save(Mockito.any(History.class));


        }
    }


    @Nested
    @DisplayName("Create History of Recipes - Negative Cases")
    class createHistoryOfRecipesWrongCases {

        @Test
        @DisplayName("Create History of Recipes - Null HistoryDTO")
        void createHistoryOfRecipesNullHistoryDTO() {

            //GIVEN

            //THEN
            assertThrows(BadRequestException.class, () -> {
                historyService.createHistory(null);
            });

            //WHEN
            Mockito.verify(historyRepository, Mockito.never()).save(Mockito.any(History.class));
            Mockito.verify(historyMapper, Mockito.never()).toEntity(Mockito.any(HistoryDTO.class));
            Mockito.verify(historyMapper, Mockito.never()).toDTO(Mockito.any(History.class));

        }

        @Test
        @DisplayName("Create History of Recipes - Null Client ID")
        void createHistoryOfRecipesNullClientId() {

            HistoryDTO historyDTO = Mockito.mock(HistoryDTO.class);

            //GIVEN
            Mockito.when(historyDTO.getClientId()).thenReturn(null);
            //THEN
            assertThrows(BadRequestException.class, () -> {
                historyService.createHistory(historyDTO);

            });

            //WHEN
            Mockito.verify(historyRepository, Mockito.never()).save(Mockito.any(History.class));
            Mockito.verify(historyMapper, Mockito.never()).toEntity(Mockito.any(HistoryDTO.class));
            Mockito.verify(historyMapper, Mockito.never()).toDTO(Mockito.any(History.class));
        }

        @Test
        @DisplayName("Create History of Recipes - Null Recipe ID")
        void createHistoryOfRecipesNullRecipeId() {

            HistoryDTO historyDTO = Mockito.mock(HistoryDTO.class);

            //GIVEN
            Mockito.when(historyDTO.getRecipe()).thenReturn(null);
            //THEN
            assertThrows(BadRequestException.class, () -> {
                historyService.createHistory(historyDTO);

            });

            //WHEN
            Mockito.verify(historyRepository, Mockito.never()).save(Mockito.any(History.class));
            Mockito.verify(historyMapper, Mockito.never()).toEntity(Mockito.any(HistoryDTO.class));
            Mockito.verify(historyMapper, Mockito.never()).toDTO(Mockito.any(History.class));
        }


        @Test
        @DisplayName("Create History of Recipes - Null Date")
        void createHistoryOfRecipesNullDate() {

            HistoryDTO historyDTO = Mockito.mock(HistoryDTO.class);
            //GIVEN
            //THEN
            assertThrows(BadRequestException.class, () -> {
                historyService.createHistory(historyDTO);

            });

            //WHEN
            Mockito.verify(historyRepository, Mockito.never()).save(Mockito.any(History.class));
            Mockito.verify(historyMapper, Mockito.never()).toEntity(Mockito.any(HistoryDTO.class));
            Mockito.verify(historyMapper, Mockito.never()).toDTO(Mockito.any(History.class));
        }

        @Test
        @DisplayName("Create History of Recipes - Incorrect Format Date")
        void createHistoryOfRecipesFailWithFormatDate() {

            //GIVEN
            HistoryDTO historyDTO = Mockito.mock(HistoryDTO.class);
            Mockito.when(historyDTO.getClientId()).thenReturn(1L);
            Mockito.when(historyDTO.getRecipe()).thenReturn(Mockito.mock(RecipeCardDTO.class));
            Mockito.when(historyDTO.getDate()).thenReturn("31-06-2005");


            //THEN
            assertThrows(BadRequestException.class, () -> {
                historyService.createHistory(historyDTO);

            });

            //WHEN
            Mockito.verify(historyRepository, Mockito.never()).save(Mockito.any(History.class));
            Mockito.verify(historyMapper, Mockito.never()).toEntity(Mockito.any(HistoryDTO.class));
            Mockito.verify(historyMapper, Mockito.never()).toDTO(Mockito.any(History.class));
        }
    }






    @Nested
    @DisplayName("Search History of Recipes Successfully")
    class SearchRecipesHistorySuccessfully{


        @Test
        @DisplayName("Search History of Recipes - Positive Case")
        void searchHistoryOfRecipesPositiveCase() {

            LocalDate date = LocalDate.now();

            Recipe recipe = new Recipe();
            recipe.setId(1L);

            History history = Mockito.mock(History.class);
            Mockito.when(history.getRecipe()).thenReturn(recipe);

            RecipeCardDTO recipeCardDTO = new RecipeCardDTO();
            recipeCardDTO.setId(1L);

            HistoryDTO dto = new HistoryDTO();
            dto.setRecipe(recipeCardDTO);


            //GIVEN
            Mockito.when(historyRepository.findAllFromLast7Days(
                    Mockito.any(LocalDate.class),
                    Mockito.any(LocalDate.class),
                    Mockito.anyLong()
            )).thenReturn(List.of(history));
            Mockito.when(historyMapper.toDTO(Mockito.any(History.class))).thenReturn(dto);
            Mockito.when(recipeRatingRepository.findAverageRatingByRecipeId(Mockito.anyLong())).thenReturn(0.0);

            //THEN
            historyService.getRecipesLast7daysByClient(date, 1L);
            //WHEN

            Mockito.verify(historyRepository).findAllFromLast7Days(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class), Mockito.anyLong());
            Mockito.verify(historyMapper).toDTO(Mockito.any(History.class));
            Mockito.verify(recipeRatingRepository).findAverageRatingByRecipeId(Mockito.anyLong());
            Mockito.verify(recipeRatingRepository, Mockito.times(1)).findAverageRatingByRecipeId(Mockito.anyLong());
            Mockito.verify(historyRepository, Mockito.times(1)).findAllFromLast7Days(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class), Mockito.anyLong());
        }

    }

    @Nested
    @DisplayName("Search History of Recipes With Errors - Negative Cases")
    class SearchRecipesHistoryWithErrors {

        @Test
        @DisplayName("Search History With Null Date - Negative Case")
        void searchHistoryOfRecipesNullDate() {

            //GIVEN

            //THEN
            assertThrows(BadRequestException.class, () -> {
                historyService.getRecipesLast7daysByClient(null, 1L);
            });

            //WHEN
            Mockito.verify(historyRepository, Mockito.never()).findAllFromLast7Days(
                    Mockito.any(LocalDate.class),
                    Mockito.any(LocalDate.class),
                    Mockito.anyLong()
            );
            Mockito.verify(historyMapper, Mockito.never()).toDTO(Mockito.any(History.class));
            Mockito.verify(recipeRatingRepository, Mockito.never()).findAverageRatingByRecipeId(Mockito.anyLong());

        }


        @Test
        @DisplayName("Search History With Null Client ID")
        void searchHistoryOfRecipesNullClientId() {
            LocalDate date = LocalDate.now();

            //GIVEN

            //THEN
            assertThrows(BadRequestException.class, () -> {
                historyService.getRecipesLast7daysByClient(date, null);
            });

            //WHEN
            Mockito.verify(historyRepository, Mockito.never()).findAllFromLast7Days(
                    Mockito.any(LocalDate.class),
                    Mockito.any(LocalDate.class),
                    Mockito.anyLong()
            );
            Mockito.verify(historyMapper, Mockito.never()).toDTO(Mockito.any(History.class));
            Mockito.verify(recipeRatingRepository, Mockito.never()).findAverageRatingByRecipeId(Mockito.anyLong());

        }


        @Test
        @DisplayName("Search History With Future Date")
        void searchHistoryOfRecipesFutureDate() {
            LocalDate date = LocalDate.now().plusDays(1);

            //GIVEN

            //THEN
            assertThrows(BadRequestException.class, () -> {
                historyService.getRecipesLast7daysByClient(date, 1L);
            });

            //WHEN
            Mockito.verify(historyRepository, Mockito.never()).findAllFromLast7Days(
                    Mockito.any(LocalDate.class),
                    Mockito.any(LocalDate.class),
                    Mockito.anyLong()
            );
            Mockito.verify(historyMapper, Mockito.never()).toDTO(Mockito.any(History.class));
            Mockito.verify(recipeRatingRepository, Mockito.never()).findAverageRatingByRecipeId(Mockito.anyLong());

        }
    }

}
