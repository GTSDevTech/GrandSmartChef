package com.grandchefsupreme.unitary_services;


import com.grandchefsupreme.dto.*;
import com.grandchefsupreme.exceptions.NotFoundException;
import com.grandchefsupreme.model.*;
import com.grandchefsupreme.model.Enums.Unit;
import com.grandchefsupreme.repository.*;
import com.grandchefsupreme.service.ClientService;
import com.grandchefsupreme.service.FavoriteCollectionService;
import com.grandchefsupreme.service.RecipeService;
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
@org.junit.jupiter.api.Tag("recipe")
@DisplayName("FavoriteCollectionService - Create Collection & Add Recipes")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FavoriteCollectionServiceTest {


    @Autowired
    private FavoriteCollectionService favoriteCollectionService;

    @Autowired
    private FavoriteCollectionRepository favoriteCollectionRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeRepository recipeRepository;


    private Client client;
    private Recipe recipe;
    private FavoriteCollection collection;
    private Ingredient ingredient;



    @BeforeEach
    void SetUp() throws IOException {

        favoriteCollectionRepository.deleteAll();
        recipeRepository.deleteAll();
        clientRepository.deleteAll();
        ingredientRepository.deleteAll();



        RegisterStep1DTO registerStep1DTO = new RegisterStep1DTO();
        registerStep1DTO.setUsername("GuilleTeSa");
        registerStep1DTO.setPassword("root");
        registerStep1DTO.setEmail("gts@gmail.com");
        Client createdClient = clientService.createClient(registerStep1DTO);
        client = clientRepository.saveAndFlush(createdClient);

        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("Vegetariano");



        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setName("Cereales");
        ingredientCategory.setDescription("Fuente principal de carbohidratos");
        ingredientCategory.setPhotoUrl("cereales.png");

        ingredientCategory = ingredientCategoryRepository.saveAndFlush(ingredientCategory);


        ingredient = new Ingredient();
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
                        () -> new AssertionError("Recipe not found"));



        FavoriteCollectionDTO favoriteCollectionDTO = new FavoriteCollectionDTO();
        favoriteCollectionDTO.setClientId(createdClient.getId());
        favoriteCollectionDTO.setTitle("Favoritas");
        favoriteCollectionDTO.setColor("#FFFFFF");
        favoriteCollectionDTO.setIsActive(true);

        FavoriteCollectionDTO createdCollection = favoriteCollectionService.createCollection(favoriteCollectionDTO);


        collection = favoriteCollectionRepository.findById(createdCollection.getId())
                .orElseThrow(() -> new RuntimeException("Collection not found"));



    }


    @Nested
    @DisplayName("Mark Recipe as Favorite - Positive Case")
    class createCollection{

        @Test
        @DisplayName("Create Collection Successfully")
        void createCollectionSuccessfully(){

            //GIVEN
            Long collectionId = collection.getId();
            Long recipeId = recipe.getId();

            assertTrue(favoriteCollectionRepository.existsById(collectionId),
                    "La colección debería existir en BBDD antes de marcar favorito");
            assertTrue(recipeRepository.existsById(recipeId),
                    "La receta debería existir en BBDD antes de marcar favorito");

            //THEN
            FavoriteCollectionDTO favoriteCollectionDTO =
                    favoriteCollectionService.addFavoriteRecipeToCollection(collectionId, recipeId);

            FavoriteCollection findCollection = favoriteCollectionRepository.findById(collectionId)
                    .orElseThrow(() -> new AssertionError("La colección debería existir"));

            assertAll("Mark Recipe Favorite - Positive Case",
                    () -> assertNotNull(favoriteCollectionDTO, "La colección no debe ser null"),
                    () -> assertEquals(collectionId, findCollection.getId()),
                    () -> assertEquals("Favoritas", favoriteCollectionDTO.getTitle(),
                            "El nombre de la colección debe mantenerse"),
                    () -> assertFalse(findCollection.getRecipes().isEmpty()),
                    () -> assertTrue(findCollection.getRecipes().stream()
                            .anyMatch(r -> r.getId().equals(recipeId)))
                    );


        }

    }

    @Nested
    @DisplayName("addFavoriteRecipeToCollection - Negative Cases")
    class AddFavoriteNegativeTests {

        @Test
        @DisplayName("Fail when collection does not exist")
        void failWhenCollectionNotFound() {
            Long invalidCollectionId = 999L;
            Long recipeId = recipe.getId();

            assertThrows(NotFoundException.class,
                    () -> favoriteCollectionService.addFavoriteRecipeToCollection(invalidCollectionId, recipeId),
                    "Debe lanzar NotFoundException si la colección no existe");
        }


        @Test
        @DisplayName("Fail when recipe does not exist")
        void failWhenRecipeNotFound() {
            Long collectionId = collection.getId();
            Long invalidRecipeId = 999L;

            assertThrows(NotFoundException.class,
                    () -> favoriteCollectionService.addFavoriteRecipeToCollection(collectionId, invalidRecipeId),
                    "Debe lanzar NotFoundException si la receta no existe");
        }

    }

}
