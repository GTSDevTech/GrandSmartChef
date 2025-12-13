package com.grandchefsupreme.controller;


import com.grandchefsupreme.dto.FavoriteCollectionDTO;
import com.grandchefsupreme.service.FavoriteCollectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
    @RequestMapping("api/favorite-collections")
@RequiredArgsConstructor
public class FavoriteCollectionController {
    private final FavoriteCollectionService favoriteCollectionService;

    @PostMapping("/create")
    public ResponseEntity<FavoriteCollectionDTO> createFavoriteCollection(@RequestBody FavoriteCollectionDTO favoriteCollectionDTO) {
        FavoriteCollectionDTO created = favoriteCollectionService.createCollection(favoriteCollectionDTO);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{collectionId}")
    public ResponseEntity<FavoriteCollectionDTO>getFavoriteCollectionById(@PathVariable Long collectionId) {

        FavoriteCollectionDTO favoriteCollectionDTO = favoriteCollectionService.getFavoriteCollection(collectionId);

        return ResponseEntity.ok(favoriteCollectionDTO);
    }


    @PostMapping("/{collectionId}/add-recipe/{recipeId}")
    public ResponseEntity<FavoriteCollectionDTO> addRecipe(@PathVariable Long collectionId, @PathVariable Long recipeId) {

        FavoriteCollectionDTO updatedCollection = favoriteCollectionService.addFavoriteRecipeToCollection(collectionId, recipeId);
        return ResponseEntity.ok(updatedCollection);
    }



    @DeleteMapping("/{collectionId}/remove/{recipeId}")
    public ResponseEntity<FavoriteCollectionDTO> removeRecipe(@PathVariable Long collectionId, @PathVariable Long recipeId) {

        FavoriteCollectionDTO updatedCollection = favoriteCollectionService.removeFavoriteRecipeFromCollection(collectionId, recipeId);

        return ResponseEntity.ok(updatedCollection);
    }

    @GetMapping("/collections")
    public ResponseEntity<List<FavoriteCollectionDTO>> getFavoriteCollections(@Valid @RequestParam Long clientId) {
        List<FavoriteCollectionDTO> list = favoriteCollectionService.getAllFavoriteCollectionByUser(clientId);
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{collectionId}")
    public ResponseEntity<Void> delete(@Valid @PathVariable Long collectionId) {
        favoriteCollectionService.delete(collectionId);
        return ResponseEntity.noContent().build();
    }
}
