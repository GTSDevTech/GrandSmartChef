package com.grandchefsupreme.mockito_services;

import com.grandchefsupreme.dto.*;
import com.grandchefsupreme.exceptions.BadRequestException;
import com.grandchefsupreme.mapper.IngredientCategoryMapper;
import com.grandchefsupreme.mapper.RecipeCardMapper;
import com.grandchefsupreme.mapper.RecipeDetailMapper;
import com.grandchefsupreme.model.*;
import com.grandchefsupreme.model.Enums.Difficulty;
import com.grandchefsupreme.model.Enums.Unit;
import com.grandchefsupreme.repository.IngredientRepository;
import com.grandchefsupreme.repository.RecipeRatingRepository;
import com.grandchefsupreme.repository.RecipeRepository;
import com.grandchefsupreme.repository.TagRepository;
import com.grandchefsupreme.service.RecipeService;
import com.grandchefsupreme.utils.FileStorageUtil;
import jakarta.persistence.EntityNotFoundException;
import org.checkerframework.checker.units.qual.N;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Mock
    public TagRepository tagRepository;

    @Mock
    public FileStorageUtil fileStorageUtil;


    @Nested
    @DisplayName("Create Recipes Positive Cases")
    class createRecipePositiveCases{


        @Test
        @DisplayName("Create Recipe - Positive Case")
        void createRecipe() throws IOException {

            MultipartFile file = Mockito.mock(MultipartFile.class);
            Mockito.when(file.isEmpty()).thenReturn(false);

            Tag tag = Mockito.mock(Tag.class);
            Mockito.when(tag.getName()).thenReturn("testTag");

            Mockito.when(tagRepository.findByNameIgnoreCase("testTag"))
                    .thenReturn(Optional.of(tag));

            Recipe recipe = Mockito.mock(Recipe.class);
            Mockito.when(recipe.getTags()).thenReturn(new HashSet<>(List.of(tag)));

            RecipeDTO recipeDTO = new RecipeDTO();
            recipeDTO.setIngredients(new ArrayList<>(List.of(new RecipeIngredientDTO())));
            recipeDTO.setSteps(new ArrayList<>(List.of(new RecipeStepDTO())));
            recipeDTO.setTags(new HashSet<>(List.of(Mockito.mock(TagDTO.class))));

            //GIVEN
            Mockito.when(recipeDetailMapper.toEntity(Mockito.any(RecipeDTO.class))).thenReturn(recipe);
            Mockito.when(fileStorageUtil.saveProfilePhoto(Mockito.any(MultipartFile.class))).thenReturn("uploads/recipe/photo.png");
            Mockito.when(recipeRepository.saveAndFlush(Mockito.any(Recipe.class))).thenReturn(Mockito.mock(Recipe.class));
            Mockito.when(recipeDetailMapper.toDto(Mockito.any(Recipe.class))).thenReturn(Mockito.mock(RecipeDTO.class));

            //THEN
            recipeService.createRecipe(recipeDTO, file);

            //WHEN

            Mockito.verify(recipeDetailMapper).toEntity(Mockito.any(RecipeDTO.class));
            Mockito.verify(ingredientRepository, Mockito.times(0)).findById(Mockito.anyLong());
            Mockito.verify(tagRepository ,Mockito.times(1)).findByNameIgnoreCase(Mockito.anyString());
            Mockito.verify(tagRepository,Mockito.times(0)).save(Mockito.any(Tag.class));
            Mockito.verify(fileStorageUtil, Mockito.times(1)).saveProfilePhoto(Mockito.any(MultipartFile.class));
            Mockito.verify(recipeRepository, Mockito.times(1)).saveAndFlush(Mockito.any(Recipe.class));
            Mockito.verify(recipeDetailMapper, Mockito.times(1)).toDto(Mockito.any(Recipe.class));




        }
    }

    @Nested
    @DisplayName("Create Recipes - Negative Cases")
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
            Mockito.verifyNoInteractions(recipeDetailMapper);

        }

        @Test
        @DisplayName("Attributes Recipe - List Null or Empty")
        void createRecipeWithoutData(){

            RecipeDTO recipeDTO = Mockito.mock(RecipeDTO.class);
            //GIVEN


            //WHEN
            assertThrows(
                    BadRequestException.class,
                    () -> {
                        Mockito.when(recipeDTO.getIngredients()).thenReturn(null);

                        recipeService.createRecipe(recipeDTO, null);

                        Mockito.verify(recipeRepository, Mockito.never()).saveAndFlush(Mockito.any(Recipe.class));
                        Mockito.verify(recipeDetailMapper, Mockito.never()).toDto(Mockito.any(Recipe.class));
                        Mockito.verify(recipeDetailMapper, Mockito.never()).toEntity(Mockito.any(RecipeDTO.class));
                    }

            );
            assertThrows(BadRequestException.class,
                    () -> {
                        Mockito.when(recipeDTO.getIngredients()).thenReturn(List.of(Mockito.mock(RecipeIngredientDTO.class)));
                        Mockito.when(recipeDTO.getSteps()).thenReturn(null);

                        recipeService.createRecipe(recipeDTO, null);

                        Mockito.verify(recipeRepository, Mockito.never()).saveAndFlush(Mockito.any(Recipe.class));
                        Mockito.verify(recipeDetailMapper, Mockito.never()).toDto(Mockito.any(Recipe.class));
                        Mockito.verify(recipeDetailMapper, Mockito.never()).toEntity(Mockito.any(RecipeDTO.class));
                    }
            );

            assertThrows(BadRequestException.class,
                    () -> {
                        Mockito.when(recipeDTO.getIngredients()).thenReturn(List.of(Mockito.mock(RecipeIngredientDTO.class)));
                        Mockito.when(recipeDTO.getSteps()).thenReturn(List.of(Mockito.mock(RecipeStepDTO.class)));
                        Mockito.when(recipeDTO.getTags()).thenReturn(null);
                        recipeService.createRecipe(recipeDTO, null);

                        Mockito.verify(recipeRepository, Mockito.never()).saveAndFlush(Mockito.any(Recipe.class));
                        Mockito.verify(recipeDetailMapper, Mockito.never()).toDto(Mockito.any(Recipe.class));
                        Mockito.verify(recipeDetailMapper, Mockito.never()).toEntity(Mockito.any(RecipeDTO.class));
                    }
            );


        }

        @Test
        @DisplayName("Create Recipe With Ingredient DB Not Found")
        void createRecipeWithIngredientNotFound(){

            Recipe recipe = Mockito.mock(Recipe.class);
            recipe.setRecipeIngredients(new HashSet<>());

            RecipeIngredient recipeIngredient = Mockito.mock(RecipeIngredient.class);

            Ingredient ingredient = Mockito.mock(Ingredient.class);
            Mockito.when(recipeIngredient.getIngredient()).thenReturn(ingredient);
            Mockito.when(ingredient.getId()).thenReturn(99L);

            RecipeIngredientDTO recipeIngredientDTO = Mockito.mock(RecipeIngredientDTO.class);
            RecipeDTO recipeDTO = Mockito.mock(RecipeDTO.class);


            //GIVEN

            assertThrows(EntityNotFoundException.class,
                    () -> {
                        Mockito.when(recipeDTO.getIngredients()).thenReturn(List.of(recipeIngredientDTO));
                        Mockito.when(recipeDTO.getSteps()).thenReturn(List.of(Mockito.mock(RecipeStepDTO.class)));
                        Mockito.when(recipeDTO.getTags()).thenReturn(new HashSet<>(List.of(new TagDTO())));

                        Mockito.when(recipeDetailMapper.toEntity(Mockito.any(RecipeDTO.class))).thenReturn(recipe);
                        Mockito.when(recipe.getRecipeIngredients()).thenReturn(new HashSet<>(List.of(recipeIngredient)));
                        Mockito.when(ingredientRepository.findById(Mockito.eq(99L))).thenReturn(Optional.empty());

                        //THEN
                        recipeService.createRecipe(recipeDTO, null);
                    }
            );
            //WHEN
            Mockito.verify(recipeDetailMapper, Mockito.times(1)).toEntity(Mockito.any(RecipeDTO.class));
            Mockito.verify(ingredientRepository, Mockito.times(1)).findById(Mockito.anyLong());
            Mockito.verify(recipeRepository, Mockito.never()).saveAndFlush(Mockito.any(Recipe.class));
            Mockito.verify(recipeDetailMapper, Mockito.never()).toDto(Mockito.any(Recipe.class));


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
