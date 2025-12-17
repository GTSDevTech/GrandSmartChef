package com.grandchefsupreme.service;

import com.grandchefsupreme.dto.RecipeRatingDTO;
import com.grandchefsupreme.exceptions.NotFoundException;
import com.grandchefsupreme.mapper.RecipeRatingMapper;
import com.grandchefsupreme.model.Client;
import com.grandchefsupreme.model.Recipe;
import com.grandchefsupreme.model.RecipeRating;
import com.grandchefsupreme.repository.ClientRepository;
import com.grandchefsupreme.repository.RecipeRatingRepository;
import com.grandchefsupreme.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RecipeRatingService {

    private final RecipeRatingRepository ratingRepository;
    private final RecipeRepository recipeRepository;
    private final ClientRepository clientRepository;
    private final RecipeRatingMapper recipeRatingMapper;

    @Transactional
    public RecipeRatingDTO rateRecipe(@Valid RecipeRatingDTO ratingDTO) {

        Recipe recipe = recipeRepository.findById(ratingDTO.getRecipeId())
                .orElseThrow(() -> new IllegalArgumentException("Receta no encontrada"));

        Client client = clientRepository.findById(ratingDTO.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        RecipeRating rating = ratingRepository
                .findByRecipeIdAndClientId(recipe.getId(), client.getId())
                .orElseGet(() -> {
                    RecipeRating r = new RecipeRating();
                    r.setRecipe(recipe);
                    r.setClient(client);
                    r.setRatingDate(LocalDateTime.now());
                    return r;
                });

        rating.setRating(ratingDTO.getRating());
        rating.setReview(ratingDTO.getReview());
        rating.setRatingDate(LocalDateTime.now());

        RecipeRating savedRatingRecipe = ratingRepository.save(rating);

        return recipeRatingMapper.toDTO(savedRatingRecipe);
    }
}
