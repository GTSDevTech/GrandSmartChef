package com.grandchefsupreme.unitary_services;


import com.grandchefsupreme.dto.*;
import com.grandchefsupreme.exceptions.BadRequestException;
import com.grandchefsupreme.exceptions.NotFoundException;
import com.grandchefsupreme.model.*;
import com.grandchefsupreme.model.Enums.Unit;
import com.grandchefsupreme.repository.*;
import com.grandchefsupreme.service.ClientService;
import com.grandchefsupreme.service.RecipeService;
import com.grandchefsupreme.service.ShoppingListService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@org.junit.jupiter.api.Tag("cart")
@DisplayName("ShoppingListService - Add Recipe to ShoppingList")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShoppingListServiceTest {

    @Autowired
    private ShoppingListService shoppingListService;

    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;


    private Recipe recipe;
    private Client client;



    @BeforeAll
    void setUp() throws IOException {

        shoppingListRepository.deleteAll();
        clientRepository.deleteAll();
        recipeRepository.deleteAll();

        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("Vegetariano");

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

        recipe = recipeRepository.findById(dto.getId())
                .orElseThrow(
                        () -> new AssertionError("Receta no encontrada"));

        RegisterStep1DTO registerStep1DTO = new RegisterStep1DTO();
        registerStep1DTO.setUsername("GuilleTeSa");
        registerStep1DTO.setPassword("root");
        registerStep1DTO.setEmail("gts@gmail.com");

        client  = clientService.createClient(registerStep1DTO);

        clientRepository.findById(client.getId())
                .orElseThrow(()-> new AssertionError("El Cliente no se ha guardado"));


    }

    @Nested
    @DisplayName("Add Recipe to Shopping List Successfully - Positive Cases")
    class addRecipeToShoppingList{

        @Test
        @DisplayName("Add Recipe Successfully - Positive Case")
        void addRecipeToShoppingListSuccessfully(){

            //GIVEN
            List<RecipeIngredient> recipeIngredientList = recipeIngredientRepository.findAllByRecipeId(recipe.getId());
            assertFalse(recipeIngredientList.isEmpty(),
                    "La receta debe de prueba debe tener al menos un ingrediente");

            //THEN
            ShoppingListDTO shoppingListSaved = shoppingListService.addRecipeToShoppingList(client.getId(), recipe.getId());


            ShoppingList findShoppingList = shoppingListRepository
                    .findById(shoppingListSaved.getId())
                    .orElseThrow(() -> new AssertionError("La lista de la compra no se ha guardado"));

            //WHEN
            assertAll("ShoppingList Create Successfully",

                () -> assertNotNull(findShoppingList.getId(), "El id de la lista no debe ser null"),
                () -> assertNotNull(findShoppingList.getClient(), "La lista debe tener un cliente asociado"),
                () -> assertEquals(client.getId(), findShoppingList.getClient().getId(),
                        "El cliente asociado debe ser el esperado"),
                () -> assertFalse(findShoppingList.getStatus(),
                        "La lista debe crearse con status=false"),
                () -> assertNotNull(findShoppingList.getItems(), "Los items no deben ser null"),
                () -> assertEquals(recipeIngredientList.size(), findShoppingList.getItems().size(),
                        "Debe haber un item en la lista por cada ingrediente de la receta"),
                () -> assertTrue(
                        findShoppingList.getItems()
                                .stream()
                                .noneMatch(ShoppingListIngredient::getBought),
                        "Todos los items deben crearse con bought=false"
                ),
                () -> {
                    RecipeIngredient ri = recipeIngredientList.getFirst();
                    boolean ingExist = findShoppingList.getItems().stream()
                            .anyMatch(sli -> sli.getIngredient().getId().equals(ri.getIngredient().getId()));

                    assertTrue(ingExist,
                            "Debe coincidir el ingrediente de la shoppingList con el de la receta");
                }

            );
        }

    }

    @Nested
    @DisplayName("Add Recipe to Shopping List - Negative Cases")
    class AddRecipeToShoppingListNegative {

        @Test
        @DisplayName("Falla cuando el cliente no existe")
        void failWhenClientNotFound() {

            Long invalidClientId = 999L;
            Long validRecipeId = recipe.getId();

            assertThrows(NotFoundException.class,
                    () -> shoppingListService.addRecipeToShoppingList(invalidClientId, validRecipeId),
                    "Debe lanzar NotFoundException si el cliente no existe");

            assertEquals(0, shoppingListRepository.count(),
                    "No debe crearse ninguna lista si el cliente no existe");
        }

        @Test
        @DisplayName("Falla cuando la receta no existe")
        void failWhenRecipeNotFound() {

            Long validClientId = client.getId();
            Long invalidRecipeId = 999L;

            assertThrows(NotFoundException.class,
                    () -> shoppingListService.addRecipeToShoppingList(validClientId, invalidRecipeId),
                    "Debe lanzar NotFoundException si la receta no existe");
            assertEquals(0, shoppingListRepository.count(),
                    "No debe crearse ninguna lista si la receta no existe");
        }

        @Test
        @DisplayName("Falla cuando cliente y receta son null - Negative Case")
        void failWhenClientAndRecipeNotFound() {

            Long invalidClientId = null;
            Long invalidRecipeId = null;

            assertThrows(BadRequestException.class,
                    () -> shoppingListService.addRecipeToShoppingList(invalidClientId, invalidRecipeId),
                    "Debe lanzar NotFoundException por el cliente no encontrado");

            assertEquals(0, shoppingListRepository.count(),
                    "No debe crearse ninguna lista si ni cliente ni receta existen");
        }

        @Test
        @DisplayName("Falla cuando existe una lista de la compra activa sin completar - Negative Case")
        void failWhenShoppingListIsActiveAndNotCompleted(){

            shoppingListService.addRecipeToShoppingList(client.getId(), recipe.getId());

            assertThrows(BadRequestException.class,
                    () -> shoppingListService.addRecipeToShoppingList(client.getId(), recipe.getId()),
                    "La lista de la compra ya existe sin completar en el carrito");

        }

    }



}
