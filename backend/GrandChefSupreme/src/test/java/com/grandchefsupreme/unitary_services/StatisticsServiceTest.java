package com.grandchefsupreme.unitary_services;


import com.grandchefsupreme.dto.*;
import com.grandchefsupreme.model.Client;
import com.grandchefsupreme.model.Enums.Unit;
import com.grandchefsupreme.model.Ingredient;
import com.grandchefsupreme.model.IngredientCategory;
import com.grandchefsupreme.model.Recipe;
import com.grandchefsupreme.repository.*;
import com.grandchefsupreme.service.ClientService;
import com.grandchefsupreme.service.FavoriteCollectionService;
import com.grandchefsupreme.service.RecipeService;
import com.grandchefsupreme.service.StatisticService;
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
@org.junit.jupiter.api.Tag("statistics")
@DisplayName("HistoryService - Create & Search Recipes History")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StatisticsServiceTest {

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    IngredientCategoryRepository ingredientCategoryRepository;


    @Autowired
    RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientService clientService;

    @Autowired
    StatisticService stadisticsService;

    @Autowired
    private FavoriteCollectionService favoriteCollectionService;

    @Autowired
    private FavoriteCollectionRepository favoriteCollectionRepository;

    private Ingredient riceIngredient;
    private Ingredient chickenIngredient;
    private Ingredient TomatoIngredient;


    @BeforeAll
    void setUpBaseData() {

        favoriteCollectionRepository.deleteAll();
        recipeIngredientRepository.deleteAll();
        recipeRepository.deleteAll();
        clientRepository.deleteAll();
        ingredientRepository.deleteAll();
        ingredientCategoryRepository.deleteAll();

        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setName("Base");
        ingredientCategory.setDescription("Categoría base para tests");
        ingredientCategory.setPhotoUrl("base.png");
        ingredientCategory = ingredientCategoryRepository.saveAndFlush(ingredientCategory);

        // Ingredientes
        riceIngredient = new Ingredient();
        riceIngredient.setName("Arroz");
        riceIngredient.setCalories(new BigDecimal("100"));
        riceIngredient.setProteins(new BigDecimal("5"));
        riceIngredient.setCarbs(new BigDecimal("30"));
        riceIngredient.setFats(new BigDecimal("1"));
        riceIngredient.setUnit(Unit.GRAMO);
        riceIngredient.setPhotoUrl("arroz.png");
        riceIngredient.setIngredientCategory(ingredientCategory);
        riceIngredient = ingredientRepository.saveAndFlush(riceIngredient);

        chickenIngredient = new Ingredient();
        chickenIngredient.setName("Pollo");
        chickenIngredient.setCalories(new BigDecimal("150"));
        chickenIngredient.setProteins(new BigDecimal("25"));
        chickenIngredient.setCarbs(new BigDecimal("0"));
        chickenIngredient.setFats(new BigDecimal("5"));
        chickenIngredient.setUnit(Unit.GRAMO);
        chickenIngredient.setPhotoUrl("pollo.png");
        chickenIngredient.setIngredientCategory(ingredientCategory);
        chickenIngredient = ingredientRepository.saveAndFlush(chickenIngredient);

        TomatoIngredient = new Ingredient();
        TomatoIngredient.setName("Tomate");
        TomatoIngredient.setCalories(new BigDecimal("20"));
        TomatoIngredient.setProteins(new BigDecimal("1"));
        TomatoIngredient.setCarbs(new BigDecimal("4"));
        TomatoIngredient.setFats(new BigDecimal("0"));
        TomatoIngredient.setUnit(Unit.GRAMO);
        TomatoIngredient.setPhotoUrl("tomate.png");
        TomatoIngredient.setIngredientCategory(ingredientCategory);
        TomatoIngredient = ingredientRepository.saveAndFlush(TomatoIngredient);



    }

    @BeforeEach
    void cleanByTest() {
        favoriteCollectionRepository.deleteAll();
        recipeIngredientRepository.deleteAll();
        recipeRepository.deleteAll();
        clientRepository.deleteAll();
    }

    private RecipeDTO buildRecipeDTO(String name, List<Ingredient> ingredients) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("Test");

        List<RecipeIngredientDTO> recipeIngredientDTOs = new ArrayList<>();
        for (Ingredient ing : ingredients) {
            IngredientDTO ingDTO = new IngredientDTO();
            ingDTO.setId(ing.getId());

            RecipeIngredientDTO ri = new RecipeIngredientDTO();
            ri.setIngredient(ingDTO);
            ri.setQuantity(new BigDecimal("100"));
            ri.setUnit(Unit.GRAMO);

            recipeIngredientDTOs.add(ri);
        }

        RecipeStepDTO step = new RecipeStepDTO();
        step.setStepNumber(1);
        step.setInstruction("Paso único");

        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setName(name);
        recipeDTO.setDifficulty("MEDIA");
        recipeDTO.setServings(2);
        recipeDTO.setPrepTime(10.0);
        recipeDTO.setAverageRating(0.0);
        recipeDTO.setDescription("Receta de prueba " + name);
        recipeDTO.setImageUrl(null);
        recipeDTO.setTags(new HashSet<>(List.of(tagDTO)));
        recipeDTO.setIngredients(recipeIngredientDTOs);
        recipeDTO.setSteps(new ArrayList<>(List.of(step)));

        return recipeDTO;
    }

    private Client createClient(String username, String email) {
        RegisterStep1DTO register = new RegisterStep1DTO();
        register.setUsername(username);
        register.setPassword("root");
        register.setEmail(email);
        return clientService.createClient(register);
    }



    @Nested
    @DisplayName("Top Ingredients - getTop5Ingredients")
    class TopIngredientsTests {

        @Test
        @DisplayName("Return Ingredients with Recipe's number ID - Positive Case")
        void showIngredientRankSuccessfully() throws IOException {

            recipeService.createRecipe(
                    buildRecipeDTO("Receta 1", List.of(riceIngredient, chickenIngredient)),
                    null
            );
            recipeService.createRecipe(
                    buildRecipeDTO("Receta 2", List.of(riceIngredient)),
                    null
            );
            recipeService.createRecipe(
                    buildRecipeDTO("Receta 3", List.of(chickenIngredient, TomatoIngredient)),
                    null
            );

            List<TopIngredientsDTO> result = stadisticsService.getTop5Ingredients();

            assertAll("Top ingredients - Positive Case",
                    () -> assertNotNull(result, "La lista no debe ser null"),
                    () -> assertFalse(result.isEmpty(), "Debe devolver al menos un ingrediente"),
                    () -> assertTrue(result.size() <= 5, "Nunca debe devolver más de 5 ingredientes"),
                    () -> {
                        TopIngredientsDTO arroz = result.stream()
                                .filter(i -> i.getIngredientName().equals("Arroz"))
                                .findFirst()
                                .orElseThrow(() -> new AssertionError("Arroz debe aparecer en el ranking"));

                        assertEquals(2L, arroz.getRecipeCount(),
                                "Arroz debe aparecer en 2 recetas");
                    },
                    () -> {
                        TopIngredientsDTO pollo = result.stream()
                                .filter(i -> i.getIngredientName().equals("Pollo"))
                                .findFirst()
                                .orElseThrow(() -> new AssertionError("Pollo debe aparecer en el ranking"));

                        assertEquals(2L, pollo.getRecipeCount(),
                                "Pollo debe aparecer en 2 recetas");
                    },
                    () -> {
                        TopIngredientsDTO tomate = result.stream()
                                .filter(i -> i.getIngredientName().equals("Tomate"))
                                .findFirst()
                                .orElseThrow(() -> new AssertionError("Tomate debe aparecer en el ranking"));

                        assertEquals(1L, tomate.getRecipeCount(),
                                "Tomate debe aparecer en 1 receta");
                    }

            );
        }

        @Test
        @DisplayName("Return Empty List When No Recipes - Negative Case")
        void showIngredientRankWhenNoRecipesReturnsEmptyList() {

            List<TopIngredientsDTO> result = stadisticsService.getTop5Ingredients();

            assertAll("Top ingredientes - sin datos",
                    () -> assertNotNull(result, "La lista no debe ser null"),
                    () -> assertTrue(result.isEmpty(),
                            "Sin recetas ni relaciones, debe devolver lista vacía")
            );

        }




    }

    @Nested
    @DisplayName("Usuarios Populares - getTopRecipes")
    class TopUsersTests {


        @Test
        @DisplayName("Top Users Favorite Recipes Populars Saved")
        void showTopUsersByMostFavoritedRecipe() throws IOException {

            RecipeDTO recetaDTO = recipeService.createRecipe(
                    buildRecipeDTO("Receta Popular", List.of(riceIngredient)),
                    null
            );
            Recipe receta = recipeRepository.findById(recetaDTO.getId())
                    .orElseThrow(() -> new AssertionError("La receta no se ha guardado"));

            Client client1 = createClient("user1", "user1@gmail.com");
            Client client2 = createClient("user2", "user2@gmail.com");

            FavoriteCollectionDTO col1 = new FavoriteCollectionDTO();
            col1.setClientId(client1.getId());
            col1.setTitle("Fav user1");
            col1.setColor("#FFFFFF");
            col1.setIsActive(true);

            FavoriteCollectionDTO col2 = new FavoriteCollectionDTO();
            col2.setClientId(client2.getId());
            col2.setTitle("Fav user2");
            col2.setColor("#FFFFFF");
            col2.setIsActive(true);

            FavoriteCollectionDTO savedCol1 = favoriteCollectionService.createCollection(col1);
            FavoriteCollectionDTO savedCol2 = favoriteCollectionService.createCollection(col2);

            favoriteCollectionService.addFavoriteRecipeToCollection(savedCol1.getId(), receta.getId());
            favoriteCollectionService.addFavoriteRecipeToCollection(savedCol2.getId(), receta.getId());

            //THEN

            List<TopClientDTO> result = stadisticsService.getTopRecipes();

            //WHEN
            assertAll("Usuarios populares - Caso positivo",
                    () -> assertNotNull(result, "La lista no debe ser null"),
                    () -> assertFalse(result.isEmpty(), "Debe devolver al menos un usuario"),
                    () -> assertEquals(2, result.size(),
                            "Deberían devolverse los 2 usuarios que tienen la receta más guardada"),
                    () -> assertTrue(
                            result.stream().anyMatch(c -> "user1@gmail.com".equals(c.getEmail())),
                            "Debe contener al usuario user1"
                    ),
                    () -> assertTrue(
                            result.stream().anyMatch(c -> "user2@gmail.com".equals(c.getEmail())),
                            "Debe contener al usuario user2"
                    )
            );
        }

        @Test
        @DisplayName("Sin favoritos devuelve lista vacía - Negative Case")
        void showTopUsersWhenNoFavoritesReturnsEmptyList() {

            List<TopClientDTO> result = stadisticsService.getTopRecipes();

            assertAll("Usuarios populares - sin datos",
                    () -> assertNotNull(result, "La lista no debe ser null"),
                    () -> assertTrue(result.isEmpty(),
                            "Sin favoritos, no debe devolver ningún usuario popular")
            );
        }
    }


}
