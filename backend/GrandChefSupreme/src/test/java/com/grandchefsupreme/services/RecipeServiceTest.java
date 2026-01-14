package com.grandchefsupreme.services;
import com.grandchefsupreme.dto.*;
import com.grandchefsupreme.exceptions.BadRequestException;
import com.grandchefsupreme.exceptions.NotFoundException;
import com.grandchefsupreme.model.Enums.Unit;
import com.grandchefsupreme.model.Ingredient;
import com.grandchefsupreme.model.IngredientCategory;
import com.grandchefsupreme.model.Recipe;
import com.grandchefsupreme.model.Tag;
import com.grandchefsupreme.repository.IngredientCategoryRepository;
import com.grandchefsupreme.repository.IngredientRepository;
import com.grandchefsupreme.repository.RecipeRepository;
import com.grandchefsupreme.repository.TagRepository;
import com.grandchefsupreme.service.IngredientService;
import com.grandchefsupreme.service.RecipeService;
import jakarta.persistence.EntityNotFoundException;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@org.junit.jupiter.api.Tag("auth")
@DisplayName("RecipeService - Create Recipe Complete & Update")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RecipeServiceTest {



    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    IngredientCategoryRepository ingredientCategoryRepository;

    private Tag tagVegetariano;
    private Tag tagVegano;
    private Ingredient ingredient;
    @Autowired
    private IngredientService ingredientService;

    @BeforeAll
    void setUpRecipeAttributes()
    {
        tagVegetariano = new Tag();
        tagVegetariano.setName("Vegetariano");
        tagVegano = new Tag();
        tagVegano.setName("Vegano");

        tagRepository.saveAll(List.of(tagVegano,tagVegetariano));

        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setName("Cereales");
        ingredientCategory.setDescription("Fuente principal de carbohidratos");
        ingredientCategory.setPhotoUrl("cereales.png");

        ingredientCategory = ingredientCategoryRepository.save(ingredientCategory);

        ingredient = new Ingredient();
        ingredient.setName("Arroz");
        ingredient.setCalories(new BigDecimal("120"));
        ingredient.setProteins(new BigDecimal("10"));
        ingredient.setCarbs(new BigDecimal("50"));
        ingredient.setFats(new BigDecimal("10"));
        ingredient.setUnit(Unit.GRAMO);
        ingredient.setPhotoUrl("arroz.png");
        ingredient.setIngredientCategory(ingredientCategory);

        ingredient = ingredientRepository.save(ingredient);

    }

    @BeforeEach
    void cleanRecipes() {
        recipeRepository.deleteAll();
    }

    @Nested
    @DisplayName("Create Recipe Successfully")
    class CreateRecipe {

        @Test
        @DisplayName("Create Positive Recipe with Default Image")
        void createRecipeSuccessfully() throws IOException {

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


            //GIVEN
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

            //THEN

            RecipeDTO dto = recipeService.createRecipe(recipeDTO, null);

            Recipe recipe = recipeRepository.findById(dto.getId())
                    .orElseThrow(
                            () -> new AssertionError("Receta no encontrada"));


            //WHEN
            assertAll("Create Recipe - Positive Cases",
                    () -> assertNotNull(recipe),
                    () -> assertNotNull(recipe.getId()),
                    () -> assertTrue(recipe.getIsActive(),
                            "La receta debe activarse automáticamente"),
                    () -> assertEquals("Paella", recipe.getName()),
                    () -> assertFalse(recipe.getRecipeIngredients().isEmpty()),
                    () -> assertEquals(1, recipe.getRecipeIngredients().size(),
                            "Solo debe existir un ingrediente persistido"),
                    () -> assertEquals("Arroz",
                            recipe.getRecipeIngredients().iterator().next().getIngredient().getName()),
                    () -> assertFalse(recipe.getRecipeSteps().isEmpty()),
                    () -> assertEquals(1, recipe.getRecipeSteps().size(),
                            "Solo debe existir un paso persistido"),
                    () -> assertThat(recipe.getRecipeSteps().getFirst().getStepNumber())
                            .as("Solo debe existir el paso número 1")
                            .isEqualTo(1),
                    () -> assertThat(recipe.getRecipeSteps().getFirst().getRecipe().getName())
                            .as("El nombre de la receta del paso debe ser Paella")
                            .isEqualTo(recipe.getName()),
                    () -> assertFalse(recipe.getTags().isEmpty()),
                    () -> assertTrue(
                            recipe.getTags().stream()
                                    .anyMatch(t -> t.getName().equalsIgnoreCase("Vegetariano"))
                            ),
                    () -> assertEquals("uploads/profile/default_profile_image.png",
                            recipe.getImageUrl())

            );


        }

        @Nested
        @DisplayName("Create Recipe With Errors")
        class CreateRecipeWithErrors {

            @Test
            @DisplayName("Negative Case - Ingredient Not Exist")
            void failWhenIngredientNotExist(){
                TagDTO tagDTO = new TagDTO();
                tagDTO.setName("Vegetariano");

                IngredientDTO ingredientDTO = new IngredientDTO();
                ingredientDTO.setId(99L);

                RecipeIngredientDTO recipeIngredientDTO = new RecipeIngredientDTO();
                recipeIngredientDTO.setIngredient(ingredientDTO);
                recipeIngredientDTO.setQuantity(new BigDecimal("200"));
                recipeIngredientDTO.setUnit(Unit.GRAMO);

                RecipeStepDTO recipeStepDTO = new RecipeStepDTO();
                recipeStepDTO.setStepNumber(1);
                recipeStepDTO.setInstruction("Pelar las verduras");


                //GIVEN
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

                assertThrows(EntityNotFoundException.class, () -> recipeService.createRecipe(recipeDTO, null),
                        "Ingrediente no existente");
            }

            @Test
            @DisplayName("Negative Case - Steps is null")
            void failWhenStepsIsEmpty(){
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


                //GIVEN
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
                recipeDTO.setSteps(null);
                assertThrows(BadRequestException.class, () -> recipeService.createRecipe(recipeDTO, null));

            }

            @Test
            @DisplayName("Negative Case - Ingredient is null")
            void failWhenIngredientIsEmpty(){
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


                //GIVEN
                RecipeDTO recipeDTO = new RecipeDTO();
                recipeDTO.setName("Paella");
                recipeDTO.setDifficulty("MEDIA");
                recipeDTO.setServings(3);
                recipeDTO.setPrepTime(20.0);
                recipeDTO.setAverageRating(null);
                recipeDTO.setDescription("Arroz valenciano");
                recipeDTO.setImageUrl(null);
                recipeDTO.setTags(new HashSet<>(List.of(tagDTO)));
                recipeDTO.setIngredients(null);
                recipeDTO.setSteps(new ArrayList<>(List.of(recipeStepDTO)));


                assertThrows(BadRequestException.class, () -> recipeService.createRecipe(recipeDTO, null));

            }

            @Test
            @DisplayName("Negative Case - Recipe is null")
            void failWhenRecipeIsNull(){

                //GIVEN
                RecipeDTO recipeDTO = null;

                assertThrows(BadRequestException.class, () -> recipeService.createRecipe(recipeDTO, null));

            }


        }



    }


    @Nested
    @DisplayName("Search Recipe with Details")
    class SearchRecipe{

        @Test
        @DisplayName("Search Successfully")
        void searchRecipeSuccessfully() throws IOException {

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


            //GIVEN
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

            //THEN
            RecipeDTO savedRecipe = recipeService.createRecipe(recipeDTO, null);
            RecipeDTO findRecipe = recipeService.getRecipeForDetails(savedRecipe.getId());

            //WHEN

            assertAll("Search - Positive Cases",
                    () -> assertNotNull(findRecipe, "La receta devuelta no debe ser null"),
                    () -> assertNotNull(findRecipe.getId(), "El id de la receta no debe ser null"),
                    () -> assertEquals(savedRecipe.getId(), findRecipe.getId(),
                            "El id devuelto debe coincidir con el de la receta creada"),
                    () -> assertEquals("Paella", findRecipe.getName()),
                    () -> assertEquals("MEDIA", findRecipe.getDifficulty()),
                    () -> assertEquals(3, findRecipe.getServings()),
                    () -> assertEquals(20.0, findRecipe.getPrepTime()),
                    () -> assertEquals("Arroz valenciano", findRecipe.getDescription()),
                    () -> assertEquals(0.0, findRecipe.getAverageRating(),
                            "Sin valoraciones, la media debe ser 0.0"),

                    // ingredientes
                    () -> assertNotNull(findRecipe.getIngredients()),
                    () -> assertFalse(findRecipe.getIngredients().isEmpty(),
                            "Debe devolver al menos un ingrediente"),
                    () -> assertEquals(1, findRecipe.getIngredients().size(),
                            "Debe haber exactamente un ingrediente"),

                    () -> {
                        RecipeIngredientDTO ing = findRecipe.getIngredients().getFirst();
                        assertNotNull(ing.getIngredient());
                        assertEquals(ingredient.getId(), ing.getIngredient().getId(),
                                "El id del ingrediente debe coincidir");
                        assertEquals(new BigDecimal("200"), ing.getQuantity());
                        assertEquals(Unit.GRAMO, ing.getUnit());
                    },
                    // pasos
                    () -> assertNotNull(findRecipe.getSteps()),
                    () -> assertFalse(findRecipe.getSteps().isEmpty(),
                            "Debe devolver al menos un paso"),
                    () -> assertEquals(1, findRecipe.getSteps().size(),
                            "Debe haber exactamente un paso"),
                    () -> {
                        RecipeStepDTO step = findRecipe.getSteps().getFirst();
                        assertEquals(1, step.getStepNumber());
                        assertEquals("Pelar las verduras", step.getInstruction());
                    },
                    // tags
                    () -> assertNotNull(findRecipe.getTags()),
                    () -> assertFalse(findRecipe.getTags().isEmpty(),
                            "Debe devolver al menos un tag"),
                    () -> assertTrue(
                            findRecipe.getTags().stream()
                                    .anyMatch(t -> t.getName().equalsIgnoreCase("Vegetariano")),
                            "Debe contener el tag 'Vegetariano'"
                    ),
                    // imagen por defecto
                    () -> assertEquals("uploads/profile/default_profile_image.png",
                            findRecipe.getImageUrl(),
                            "Debe tener la imagen por defecto al no subir foto"),

                    // info nutricional calculada
                    () -> assertNotNull(findRecipe.getNutritionInfo(),
                            "La información nutricional no debe ser null")


            );

        }

        @Test
        @DisplayName("Recipe not found - Negative Case")
        void searchRecipeNotFound(){

            assertThrows(NotFoundException.class, () -> recipeService.getRecipeForDetails(99L),
                    "Debe lanzar NotFoundException si la receta no existe o no está activa");

        }

    }

}
