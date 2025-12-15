package com.grandchefsupreme.controller;


import com.grandchefsupreme.dto.FavoriteCollectionDTO;
import com.grandchefsupreme.service.FavoriteCollectionService;
import com.grandchefsupreme.utils.ApiResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
    @RequestMapping("api/favorite-collections")
@RequiredArgsConstructor
public class FavoriteCollectionController {
    private final FavoriteCollectionService favoriteCollectionService;

    @PostMapping("/create")
    public FavoriteCollectionDTO createFavoriteCollection(
            HttpServletRequest request,
            @RequestBody @Valid FavoriteCollectionDTO favoriteCollectionDTO
    ) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Colección creada correctamente"
        );

        return favoriteCollectionService.createCollection(favoriteCollectionDTO);
    }

    @GetMapping("/{collectionId}")
    public FavoriteCollectionDTO getFavoriteCollectionById(
            HttpServletRequest request,
            @Valid @PathVariable Long collectionId
    ) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Colección obtenida correctamente"
        );

        return favoriteCollectionService.getFavoriteCollection(collectionId);
    }


    @PostMapping("/{collectionId}/add-recipe/{recipeId}")
    public FavoriteCollectionDTO addRecipe(
            HttpServletRequest request,
            @PathVariable Long collectionId,
            @PathVariable Long recipeId
    ) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Receta añadida a la colección"
        );

        return favoriteCollectionService.addFavoriteRecipeToCollection(collectionId, recipeId);
    }


    @DeleteMapping("/{collectionId}/remove/{recipeId}")
    public FavoriteCollectionDTO removeRecipe(
            HttpServletRequest request,
            @PathVariable Long collectionId,
            @PathVariable Long recipeId
    ) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Receta eliminada de la colección"
        );

        return favoriteCollectionService.removeFavoriteRecipeFromCollection(collectionId, recipeId);
    }


    @GetMapping("/collections")
    public List<FavoriteCollectionDTO> getFavoriteCollections(
            HttpServletRequest request,
            @RequestParam @Valid Long clientId
    ) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Colecciones obtenidas correctamente"
        );

        return favoriteCollectionService.getAllFavoriteCollectionByUser(clientId);
    }


    @DeleteMapping("/{collectionId}")
    public Void delete(
            HttpServletRequest request,
            @PathVariable @Valid Long collectionId
    ) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Colección eliminada correctamente"
        );

        favoriteCollectionService.delete(collectionId);
        return null;
    }
}