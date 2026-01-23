package com.grandchefsupreme.service;

import com.grandchefsupreme.dto.TopClientDTO;
import com.grandchefsupreme.dto.TopIngredientsDTO;
import com.grandchefsupreme.repository.ClientRepository;
import com.grandchefsupreme.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StatisticService {

    private final ClientRepository clientRepository;
    private final IngredientRepository ingredientRepository;

    public List<TopClientDTO> getTopRecipes() {
        return clientRepository.findClientByTopRecipes();
    }

    public List<TopIngredientsDTO> getTop5Ingredients() {

        return ingredientRepository.findTop5IngredientsByRecipeCount();
    }

}
