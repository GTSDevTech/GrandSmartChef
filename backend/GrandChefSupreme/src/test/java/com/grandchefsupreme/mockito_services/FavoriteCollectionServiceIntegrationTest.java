package com.grandchefsupreme.mockito_services;

import com.grandchefsupreme.dto.ClientDTO;
import com.grandchefsupreme.dto.FavoriteCollectionDTO;
import com.grandchefsupreme.exceptions.NotFoundException;
import com.grandchefsupreme.mapper.FavoriteCollectionMapper;
import com.grandchefsupreme.model.Client;
import com.grandchefsupreme.model.FavoriteCollection;
import com.grandchefsupreme.model.Recipe;
import com.grandchefsupreme.repository.ClientRepository;
import com.grandchefsupreme.repository.FavoriteCollectionRepository;
import com.grandchefsupreme.repository.RecipeRepository;
import com.grandchefsupreme.service.FavoriteCollectionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class FavoriteCollectionServiceIntegrationTest {

    @InjectMocks
    public FavoriteCollectionService favoriteCollectionService;

    @Mock
    public FavoriteCollectionRepository favoriteCollectionRepository;

    @Mock
    public RecipeRepository recipeRepository;

    @Mock
    public FavoriteCollectionMapper favoriteCollectionMapper;




    @Nested
    @DisplayName("Add Recipe to Collection Successfully - Positive Cases")
    class AddRecipeToCollectionSuccessfully{

        @Test
        @DisplayName("Add Recipe To Collection - Positive Case")
        void addRecipeToCollectionPositiveCase(){


            //GIVEN

            Mockito.when(favoriteCollectionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(Mockito.mock(FavoriteCollection.class)));
            Mockito.when(recipeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(Mockito.mock(Recipe.class)));
            Mockito.when(favoriteCollectionMapper.toDTO(Mockito.any(FavoriteCollection.class))).thenReturn(Mockito.mock(FavoriteCollectionDTO.class));
            Mockito.when(favoriteCollectionRepository.saveAndFlush(Mockito.any(FavoriteCollection.class))).thenReturn(Mockito.mock(FavoriteCollection.class));

            //THEN
            favoriteCollectionService.addFavoriteRecipeToCollection(1L, 1L);

            //WHEN
            Mockito.verify(favoriteCollectionRepository, Mockito.times(1)).saveAndFlush(Mockito.any(FavoriteCollection.class));
            Mockito.verify(recipeRepository, Mockito.times(1)).findById(Mockito.anyLong());
            Mockito.verify(favoriteCollectionRepository, Mockito.times(1)).findById(Mockito.anyLong());
            Mockito.verify(favoriteCollectionMapper, Mockito.times(1)).toDTO(Mockito.any(FavoriteCollection.class));


        }
    }

    @Nested
    @DisplayName("Add Recipe to Collection - Negative Cases")
    class AddRecipeToCollectionNegativeCases {

            @Test
            @DisplayName("Add Recipe To Collection Collection Not Extists")
            void addRecipeToCollectionNotExists() {
                FavoriteCollectionDTO favoriteCollectionDTO = Mockito.mock(FavoriteCollectionDTO.class);

                //GIVEN

                assertThrows(NotFoundException.class,
                        () -> {
                            favoriteCollectionService.addFavoriteRecipeToCollection(99L, 1L);

                            Mockito.verify(favoriteCollectionRepository, Mockito.never()).saveAndFlush(Mockito.any(FavoriteCollection.class));
                            Mockito.verify(recipeRepository, Mockito.never()).findById(Mockito.anyLong());
                            Mockito.verify(favoriteCollectionRepository, Mockito.times(1)).findById(Mockito.anyLong());
                            Mockito.verify(favoriteCollectionMapper, Mockito.never()).toDTO(Mockito.any(FavoriteCollection.class));
                        }

                );
            }


        @Test
        @DisplayName("Add Recipe To Collection Recipe Not Extists")
        void addRecipeToCollectionRecipeNotExists() {

                assertThrows(NotFoundException.class,
                    () -> {
                        favoriteCollectionService.addFavoriteRecipeToCollection(1L, 99L);

                        Mockito.verify(favoriteCollectionRepository, Mockito.never()).saveAndFlush(Mockito.any(FavoriteCollection.class));
                        Mockito.verify(recipeRepository, Mockito.times(1)).findById(Mockito.anyLong());
                        Mockito.verify(favoriteCollectionRepository, Mockito.times(1)).findById(Mockito.anyLong());
                        Mockito.verify(favoriteCollectionMapper, Mockito.never()).toDTO(Mockito.any(FavoriteCollection.class));
                    }
            );

        }
    }


}
