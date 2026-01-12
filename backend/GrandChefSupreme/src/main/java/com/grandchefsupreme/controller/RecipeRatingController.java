package com.grandchefsupreme.controller;


import com.grandchefsupreme.dto.RecipeRatingDTO;
import com.grandchefsupreme.service.RecipeRatingService;
import com.grandchefsupreme.utils.ApiResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rating")
@RequiredArgsConstructor
public class RecipeRatingController {

    private final RecipeRatingService recipeRatingService;

    @PostMapping()
    public RecipeRatingDTO rateRecipe(
            HttpServletRequest request,
            @Valid @RequestBody RecipeRatingDTO ratingDTO
    ){
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Receta valorada correctamente"
        );

        return recipeRatingService.rateRecipe(ratingDTO);

    }

}
