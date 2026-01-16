package com.grandchefsupreme.controller;

import com.grandchefsupreme.dto.TopClientDTO;
import com.grandchefsupreme.dto.TopIngredientsDTO;
import com.grandchefsupreme.service.StadisticService;
import com.grandchefsupreme.utils.ApiResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StadisticService stadisticService;

    @GetMapping("/top5ingredients")
    public List<TopIngredientsDTO> getTop5Ingredients(HttpServletRequest request) {

        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Top 5 ingredientes más utilizados"
        );

        return stadisticService.getTop5Ingredients();
    }


    @GetMapping("/clientWithFavoriteRecipes")
    public List<TopClientDTO> getClientWithFavoriteRecipes(HttpServletRequest request) {

        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Clientes con más recetas favoritas"
        );

        return stadisticService.getTopRecipes();
    }

}
