package com.grandchefsupreme.mockito_services;

import com.grandchefsupreme.dto.ShoppingListDTO;
import com.grandchefsupreme.exceptions.BadRequestException;
import com.grandchefsupreme.mapper.ShoppingListMapper;
import com.grandchefsupreme.model.*;
import com.grandchefsupreme.repository.*;
import com.grandchefsupreme.service.ShoppingListService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ShoppingListServiceIntegrationTest {

    @InjectMocks
    public ShoppingListService shoppingListService;

    @Mock
    public ShoppingListRepository shoppingListRepository;

    @Mock
    public RecipeRepository recipeRepository;

    @Mock
    public ClientRepository clientRepository;

    @Mock
    public RecipeIngredientRepository recipeIngredientRepository;

    @Mock
    public ShoppingListIngredientRepository shoppingListIngredientRepository;

    @Mock
    public ShoppingListMapper shoppingListMapper;





    @Nested
    @DisplayName("Add Recipe to Shopping List Successfully")
    class AddRecipeToCollectionSuccessfully{

        @Test
        @DisplayName("Add Recipe To Shopping List - Positive Case")
        void addRecipeToShoppingListPositiveCase(){

            ShoppingListDTO dto = Mockito.mock(ShoppingListDTO.class);
            Mockito.when(dto.getStatus()).thenReturn(true);



            ShoppingList saved = new ShoppingList();
            saved.setId(1L);
            saved.setStatus(true);
            saved.setItems(new ArrayList<>());

            //GIVEN

            Mockito.when(shoppingListRepository.findAllByClientId(Mockito.anyLong())).thenReturn(List.of(new ShoppingList()));
            Mockito.when(shoppingListMapper.toDTO(Mockito.anyList())).thenReturn(List.of(dto));
            Mockito.when(clientRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Client()));
            Mockito.when(recipeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Recipe()));
            Mockito.when(shoppingListRepository.save(Mockito.any(ShoppingList.class))).thenReturn(saved);
            Mockito.when(recipeIngredientRepository.findAllByRecipeId(Mockito.anyLong())).thenReturn(List.of(new RecipeIngredient()));
            Mockito.when(shoppingListRepository.findByIdWithItems(1L)).thenReturn(Optional.of(saved));
            Mockito.when(shoppingListMapper.toDTO(Mockito.any(ShoppingList.class))).thenReturn(Mockito.mock(ShoppingListDTO.class));


            //THEN

            shoppingListService.addRecipeToShoppingList(1L, 1L);

            //WHEN

            Mockito.verify(shoppingListRepository).save(Mockito.any(ShoppingList.class));
            Mockito.verify(recipeRepository).findById(Mockito.anyLong());
            Mockito.verify(clientRepository).findById(Mockito.anyLong());
            Mockito.verify(recipeIngredientRepository).findAllByRecipeId(Mockito.anyLong());
            Mockito.verify(shoppingListMapper).toDTO(Mockito.any(ShoppingList.class));



        }
    }



    @Nested
    @DisplayName("Add Recipe to Shopping List - Negative Cases")
    class AddRecipeToCollectionNegativeCases {

        @Test
        @DisplayName("Add Recipe To Collection With Errors")
        void AddRecipeToCollectionWithNullUser() {

            assertThrows(BadRequestException.class,
                    () -> {
                        shoppingListService.addRecipeToShoppingList(null, 1L);
                        Mockito.verify(shoppingListRepository, Mockito.never()).save(Mockito.any(ShoppingList.class));
                        Mockito.verify(recipeRepository, Mockito.never()).findById(Mockito.anyLong());
                        Mockito.verify(shoppingListRepository, Mockito.never()).existsItemWithRecipeInActiveShoppingList(Mockito.anyLong());
                        Mockito.verify(clientRepository, Mockito.never()).findById(Mockito.anyLong());
                        Mockito.verify(recipeIngredientRepository, Mockito.never()).findAllByRecipeId(Mockito.anyLong());
                        Mockito.verify(shoppingListIngredientRepository, Mockito.never()).save(Mockito.any(ShoppingListIngredient.class));
                        Mockito.verify(shoppingListMapper, Mockito.never()).toDTO(Mockito.any(ShoppingList.class));
                        Mockito.verify(shoppingListMapper, Mockito.never()).toDTO(Mockito.any(ShoppingList.class));

                    }
            );


        }

        @Test
        @DisplayName("Add Recipe To Collection With Errors")
        void AddRecipeToCollectionWithNullRecipe() {
            assertThrows(BadRequestException.class,
                    () -> {
                        shoppingListService.addRecipeToShoppingList(1L, null);
                        Mockito.verify(shoppingListRepository, Mockito.never()).save(Mockito.any(ShoppingList.class));
                        Mockito.verify(recipeRepository, Mockito.never()).findById(Mockito.anyLong());
                        Mockito.verify(shoppingListRepository, Mockito.never()).existsItemWithRecipeInActiveShoppingList(Mockito.anyLong());
                        Mockito.verify(clientRepository, Mockito.never()).findById(Mockito.anyLong());
                        Mockito.verify(recipeIngredientRepository, Mockito.never()).findAllByRecipeId(Mockito.anyLong());
                        Mockito.verify(shoppingListIngredientRepository, Mockito.never()).save(Mockito.any(ShoppingListIngredient.class));
                        Mockito.verify(shoppingListMapper, Mockito.never()).toDTO(Mockito.any(ShoppingList.class));
                        Mockito.verify(shoppingListMapper, Mockito.never()).toDTO(Mockito.any(ShoppingList.class));

                    }
            );
        }


        @Test
        @DisplayName("Add Recipe To Collection With Errors")
        void AddRecipeToCollectionAlreadyExisting() {

            assertThrows(BadRequestException.class,
                    () -> {
                        shoppingListService.addRecipeToShoppingList(1L, 1L);
                        Mockito.verify(shoppingListRepository, Mockito.never()).save(Mockito.any(ShoppingList.class));
                        Mockito.verify(recipeRepository, Mockito.never()).findById(Mockito.anyLong());
                        Mockito.verify(shoppingListRepository, Mockito.times(1)).existsItemWithRecipeInActiveShoppingList(Mockito.anyLong());
                        Mockito.verify(clientRepository, Mockito.never()).findById(Mockito.anyLong());
                        Mockito.verify(recipeIngredientRepository, Mockito.never()).findAllByRecipeId(Mockito.anyLong());
                        Mockito.verify(shoppingListIngredientRepository, Mockito.never()).save(Mockito.any(ShoppingListIngredient.class));
                        Mockito.verify(shoppingListMapper, Mockito.never()).toDTO(Mockito.any(ShoppingList.class));
                        Mockito.verify(shoppingListMapper, Mockito.never()).toDTO(Mockito.any(ShoppingList.class));
                    }
            );
        }
    }
}
