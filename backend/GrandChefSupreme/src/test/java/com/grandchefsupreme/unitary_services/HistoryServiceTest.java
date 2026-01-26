package com.grandchefsupreme.unitary_services;


import com.grandchefsupreme.dto.*;
import com.grandchefsupreme.exceptions.BadRequestException;
import com.grandchefsupreme.model.*;
import com.grandchefsupreme.model.Enums.Unit;
import com.grandchefsupreme.repository.*;
import com.grandchefsupreme.service.ClientService;
import com.grandchefsupreme.service.HistoryService;
import com.grandchefsupreme.service.RecipeService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@org.junit.jupiter.api.Tag("history")
@DisplayName("HistoryService - Create & Search Recipes History")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HistoryServiceTest {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private HistoryService historyService;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private IngredientRepository ingredientRepository;


    private RecipeCardDTO recipeCardDTO;
    private Client client;

    @BeforeAll
    void setUp() throws IOException {
        clientRepository.deleteAll();
        recipeRepository.deleteAll();
        ingredientCategoryRepository.deleteAll();
        ingredientRepository.deleteAll();

        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setName("Cereales");
        ingredientCategory.setDescription("Fuente principal de carbohidratos");
        ingredientCategory.setPhotoUrl("cereales.png");

        ingredientCategory = ingredientCategoryRepository.saveAndFlush(ingredientCategory);

        Ingredient ingredient = new Ingredient();
        ingredient.setName("Arroz");
        ingredient.setCalories(new BigDecimal("120"));
        ingredient.setProteins(new BigDecimal("10"));
        ingredient.setCarbs(new BigDecimal("50"));
        ingredient.setFats(new BigDecimal("10"));
        ingredient.setUnit(Unit.GRAMO);
        ingredient.setPhotoUrl("arroz.png");
        ingredient.setIngredientCategory(ingredientCategory);

        ingredient = ingredientRepository.saveAndFlush(ingredient);

        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("Vegetariano");

        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setId(ingredient.getId());

        RecipeIngredientDTO recipeIngredientDTO = new RecipeIngredientDTO();
        recipeIngredientDTO.setIngredient(ingredientDTO);
        recipeIngredientDTO.setQuantity(new BigDecimal("200"));
        recipeIngredientDTO.setUnit(Unit.GRAMO);

        RecipeStepDTO recipeStepDTO = new RecipeStepDTO();
        recipeStepDTO.setStepNumber(1);
        recipeStepDTO.setInstruction("Pelar las verduras");

        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setName("Paella");
        recipeDTO.setDifficulty("MEDIA");
        recipeDTO.setServings(3);
        recipeDTO.setPrepTime(20.0);
        recipeDTO.setAverageRating(null);
        recipeDTO.setDescription("Arroz valenciano");
        recipeDTO.setImageUrl(null);
        recipeDTO.setTags(new HashSet<>(List.of(tagDTO)));
        recipeDTO.setIngredients(new ArrayList<>(List.of(recipeIngredientDTO)));
        recipeDTO.setSteps(new ArrayList<>(List.of(recipeStepDTO)));

        RecipeDTO dto = recipeService.createRecipe(recipeDTO, null);

        Recipe recipe = recipeRepository.findById(dto.getId())
                .orElseThrow(
                        () -> new AssertionError("Receta no encontrada"));


        recipeCardDTO = new RecipeCardDTO();
        recipeCardDTO.setId(recipe.getId());
        recipeCardDTO.setName(recipe.getName());
        recipeCardDTO.setServings(recipe.getServings());
        recipeCardDTO.setDifficulty("MEDIA");
        recipeCardDTO.setImageUrl(recipe.getImageUrl());
        recipeCardDTO.setTags(new HashSet<>(List.of(tagDTO)));
        recipeCardDTO.setAverageRating(5.0);


        RegisterStep1DTO registerStep1DTO = new RegisterStep1DTO();
        registerStep1DTO.setUsername("GuilleTeSa");
        registerStep1DTO.setPassword("root");
        registerStep1DTO.setEmail("gts@gmail.com");

        client  = clientService.createClient(registerStep1DTO);

        clientRepository.findById(client.getId())
                .orElseThrow(()-> new AssertionError("El Cliente no se ha guardado"));

    }



    @BeforeEach
    void cleanDb(){
        historyRepository.deleteAll();
    }


    @Nested
    @DisplayName("Create History Successfully")
    class CreateHistory {

        @Test
        @DisplayName("Create History Successfully One Select Day - Successfully")
        void createHistorySuccessfullyOnOneSelectDay() {

            HistoryDTO historyDTO = new HistoryDTO();
            historyDTO.setClientId(client.getId());
            historyDTO.setRecipe(recipeCardDTO);
            historyDTO.setDate("1999-02-22");

            HistoryDTO historySaved = historyService.createHistory(historyDTO);
            History findHistory = historyRepository.findById(historySaved.getId())
                    .orElseThrow(()-> new AssertionError("La historia no se ha guardado en BBDD"));

            assertAll("History Created Successfully - Positive Cases",

                    () -> assertNotNull(findHistory.getId(), "La entidad persistida debe tener id"),
                    () -> assertEquals(client.getId(),
                            findHistory.getClient().getId(),
                            "El cliente asociado debe ser el esperado"),
                    () -> assertEquals(recipeCardDTO.getId(),
                            findHistory.getRecipe().getId(),
                            "La receta asociada debe ser la esperada"),
                    () -> assertEquals(LocalDate.parse("1999-02-22"),
                            findHistory.getDate(),
                            "La fecha debe coincidir con la enviada")
            );
        }

    }

    @Nested
    @DisplayName("Create History With Errors")
    class CreateHistoryWithErrors {

        @Test
        @DisplayName("Falla si el HistoryDTO es null - Negative Case")
        void failWhenHistoryIsNull() {

            assertThrows(BadRequestException.class,
                    () -> historyService.createHistory(null),
                    "Debería fallar al ser History null");
        }


        @Test
        @DisplayName("Falla si el clientId es null - Negative Case")
        void failWhenClientIdIsNull() {

            HistoryDTO historyDTO = new HistoryDTO();
            historyDTO.setClientId(null);
            historyDTO.setRecipe(recipeCardDTO);
            historyDTO.setDate("2000-01-01");


            assertThrows(BadRequestException.class,
                    () -> historyService.createHistory(historyDTO),
                    "Debería fallar por constraint NOT NULL en id_user");
        }

        @Test
        @DisplayName("Falla si la receta es null")
        void failWhenRecipeIsNull() {

            HistoryDTO historyDTO = new HistoryDTO();
            historyDTO.setClientId(client.getId());
            historyDTO.setRecipe(null);
            historyDTO.setDate("2000-01-01");

            assertThrows(BadRequestException.class,
                    () -> historyService.createHistory(historyDTO),
                    "Debería fallar por constraint NOT NULL en id_recipe");
        }


        @Test
        @DisplayName("Falla si la fecha es null - Negative Case")
        void failWhenDateIsNull() {

            HistoryDTO historyDTO = new HistoryDTO();
            historyDTO.setClientId(client.getId());
            historyDTO.setRecipe(recipeCardDTO);
            historyDTO.setDate(null);

            assertThrows(RuntimeException.class,
                    () -> historyService.createHistory(historyDTO),
                    "Con date = null el mapper debería fallar");
        }

        @Test
        @DisplayName("Falla si la fecha no tiene formato yyyy-MM-dd - Negative Case")
        void failWhenDateHasWrongFormat() {

            HistoryDTO historyDTO = new HistoryDTO();
            historyDTO.setClientId(client.getId());
            historyDTO.setRecipe(recipeCardDTO);
            historyDTO.setDate("1999/02/22");

            assertThrows(BadRequestException.class,
                    () -> historyService.createHistory(historyDTO),
                    "Debe fallar al parsear una fecha con formato incorrecto");
        }

    }


    @Nested
    @DisplayName("HistoryService - Consultar historial ")
    class ConsultarHistory {

        @Test
        @DisplayName("Search Recipe History Successfully - Positive Cases")
        void searchRecipeHistorySuccessfully(){

            HistoryDTO historyDTO = new HistoryDTO();
            historyDTO.setClientId(client.getId());
            historyDTO.setRecipe(recipeCardDTO);
            historyDTO.setDate(LocalDate.now().toString());

            HistoryDTO historySaved = historyService.createHistory(historyDTO);

            historyRepository.findById(historySaved.getId())
                    .orElseThrow(() -> new AssertionError("La historial de la receta no se ha guardado"));

            //THEN
            LocalDate today = LocalDate.now();
            List<HistoryDTO> findHistoryDTO = historyService.getRecipesLast7daysByClient(today, client.getId());

            //WHEN
            assertAll("Search History successfully",
                    () -> assertEquals(findHistoryDTO.getFirst().getDate(),
                            LocalDate.parse(historyDTO.getDate())
                                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            "La fecha devuelta debe coincidir con la guardada con formato europeo"),
                    () -> assertNotNull(findHistoryDTO, "La lista no debe ser null"),
                    () -> assertThat(findHistoryDTO.size())
                            .as("Debe devolver exactamente un registro")
                            .isEqualTo(1),
                    () -> assertEquals(client.getId(),
                            findHistoryDTO.getFirst().getClientId(),
                            "El historial debe pertenecer al cliente indicado"),
                    () -> assertEquals(recipeCardDTO.getId(),
                            findHistoryDTO.getFirst().getRecipe().getId(),
                            "La receta devuelta debe coincidir")

            );

        }

        @Test
        @DisplayName("No devuelve recetas si no hay historial en los últimos 7 días - Negative Case")
        void returnEmptyWhenNoHistory() {
            LocalDate today = LocalDate.now();

            List<HistoryDTO> histories =
                    historyService.getRecipesLast7daysByClient(today, client.getId());

            assertAll(
                    () -> assertNotNull(histories),
                    () -> assertTrue(histories.isEmpty(),
                            "Debe devolver lista vacía si no hay historial")
            );
        }

        @Test
        @DisplayName("Falla cuando la fecha es nula - Negative Case")
        void failWhenDateIsNull() {

            assertThrows(BadRequestException.class,
                    () -> historyService.getRecipesLast7daysByClient(null, client.getId()),
                    "Debe lanzar BadRequestException si la fecha es null");
        }


        @Test
        @DisplayName("Falla cuando la fecha es futura - Negative Case")
        void failWhenDateIsFuture() {

            LocalDate futureDate = LocalDate.now().plusDays(1);

            assertThrows(BadRequestException.class,
                    () -> historyService.getRecipesLast7daysByClient(futureDate, client.getId()),
                    "Debe lanzar BadRequestException si la fecha es futura");
        }

        @Test
        @DisplayName("Falla cuando el clientId es nulo - Negative Case")
        void failWhenClientIdIsNull() {

            LocalDate today = LocalDate.now();

            assertThrows(BadRequestException.class,
                    () -> historyService.getRecipesLast7daysByClient(today, null),
                    "Debe lanzar BadRequestException si el clientId es null");
        }

    }

}
