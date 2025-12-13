package com.grandchefsupreme.controller;

import com.grandchefsupreme.dto.ClientRegisterDTO;
import com.grandchefsupreme.dto.TopClientDTO;
import com.grandchefsupreme.dto.TopIngredientsDTO;
import com.grandchefsupreme.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping("/top5ingredients")
    public List<TopIngredientsDTO> getTop5Ingredients() {
        return statisticsService.getTop5Ingredients();
    }

    @GetMapping("/clientWithFavoriteRecipes")
    public List<TopClientDTO> getClientWithFavoriteRecipes() {
        return statisticsService.getTopRecipes();
    }

}
