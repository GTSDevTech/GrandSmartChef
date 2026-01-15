package com.grandchefsupreme.service;

import com.grandchefsupreme.dto.FavoriteCollectionDTO;
import com.grandchefsupreme.exceptions.NotFoundException;
import com.grandchefsupreme.mapper.FavoriteCollectionMapper;
import com.grandchefsupreme.model.Client;
import com.grandchefsupreme.model.FavoriteCollection;
import com.grandchefsupreme.model.Recipe;
import com.grandchefsupreme.repository.ClientRepository;
import com.grandchefsupreme.repository.FavoriteCollectionRepository;
import com.grandchefsupreme.repository.RecipeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteCollectionService {


    private final FavoriteCollectionMapper favoriteCollectionMapper;
    private final ClientRepository clientRepository;
    private final FavoriteCollectionRepository favoriteCollectionRepository;
    private final RecipeRepository recipeRepository;


    public FavoriteCollectionDTO createCollection(FavoriteCollectionDTO favoriteCollectionDTO) {
        Client client = clientRepository.findById(favoriteCollectionDTO.getClientId())
                .orElseThrow(() -> new NotFoundException("Client not found"));
        FavoriteCollection collection = favoriteCollectionMapper.toEntity(favoriteCollectionDTO);
        collection.setClient(client);
        FavoriteCollection savedCollection = favoriteCollectionRepository.save(collection);

        return favoriteCollectionMapper.toDTO(savedCollection);
    }


    public FavoriteCollectionDTO getFavoriteCollection(Long collectionId) {
        FavoriteCollection collection = favoriteCollectionRepository.findById(collectionId)
                .orElseThrow(() -> new NotFoundException("Collection not found"));
        return favoriteCollectionMapper.toDTO(collection);
    }

    public FavoriteCollectionDTO addFavoriteRecipeToCollection(Long collectionId, Long recipeId) {
        FavoriteCollection collection = favoriteCollectionRepository.findById(collectionId)
                .orElseThrow(() -> new NotFoundException("Collection not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NotFoundException("Recipe not found"));

        collection.setRecipes(new HashSet<>(List.of(recipe)));
        favoriteCollectionRepository.saveAndFlush(collection);

        return favoriteCollectionMapper.toDTO(collection);
    }

    public FavoriteCollectionDTO removeFavoriteRecipeFromCollection(Long collectionId, Long recipeId) {
        FavoriteCollection collection = favoriteCollectionRepository.findById(collectionId)
                .orElseThrow(() -> new NotFoundException("Collection not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NotFoundException("Recipe not found"));

        collection.getRecipes().remove(recipe);
        favoriteCollectionRepository.save(collection);

        return favoriteCollectionMapper.toDTO(collection);
    }



    public List<FavoriteCollectionDTO> getAllFavoriteCollectionByUser(Long clientId) {
        List<FavoriteCollection> collectionList =  favoriteCollectionRepository.findByClientId(clientId);
        return favoriteCollectionMapper.toDTO(collectionList);
    }


    public void delete(Long collectionId) {
        favoriteCollectionRepository.deleteById(collectionId);
    }


    public Long countAllCollectionByUser(Long clientId) {
        return favoriteCollectionRepository.countFavoriteCollectionByClient_Id(clientId);
    }

    public Long countAllFavoriteRecipesByUser(@Valid Long clientId) {

        return favoriteCollectionRepository.countFavoriteRecipesByClientId(clientId);
    }
}
