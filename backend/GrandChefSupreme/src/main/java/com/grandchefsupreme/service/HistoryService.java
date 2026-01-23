package com.grandchefsupreme.service;

import com.grandchefsupreme.dto.HistoryDTO;
import com.grandchefsupreme.exceptions.BadRequestException;
import com.grandchefsupreme.mapper.HistoryMapper;
import com.grandchefsupreme.model.Client;
import com.grandchefsupreme.model.History;
import com.grandchefsupreme.model.Recipe;
import com.grandchefsupreme.repository.ClientRepository;
import com.grandchefsupreme.repository.HistoryRepository;
import com.grandchefsupreme.repository.RecipeRatingRepository;
import com.grandchefsupreme.repository.RecipeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class    HistoryService {

    private final HistoryRepository historyRepository;
    private final RecipeRatingRepository recipeRatingRepository;
    private final HistoryMapper historyMapper;

    public HistoryDTO createHistory(HistoryDTO historyDTO){

        if (historyDTO == null) {
            throw new BadRequestException("Historial obligatorio");
        }
        if (historyDTO.getClientId() == null) {
            throw new BadRequestException("Usuario no logueado");
        }
        if (historyDTO.getRecipe() == null || historyDTO.getRecipe().getId() == null) {
            throw new BadRequestException("Receta obligatoria");
        }
        if (historyDTO.getDate() == null) {
            throw new BadRequestException("Fecha obligatoria");
        }

        LocalDate date;
        try {
            date = LocalDate.parse(historyDTO.getDate());
        } catch (DateTimeParseException ex) {
            throw new BadRequestException("La fecha debe tener formato yyyy-MM-dd");
        }

        historyDTO.setDate(String.valueOf(date));

        History history =  historyMapper.toEntity(historyDTO);

        history = historyRepository.save(history);
        return historyMapper.toDTO(history);

    }

    public List<HistoryDTO> getRecipesLast7daysByClient(LocalDate date, Long clientId) {

        if (date == null || date.isAfter(LocalDate.now())) {
            throw new BadRequestException("La fecha es incorrecta");
        }
        if (clientId == null) {
            throw new BadRequestException("El usuario es obligatorio");
        }

        LocalDate sevenDaysAgo = date.minusDays(7);
        return historyRepository
                .findAllFromLast7Days(sevenDaysAgo, date, clientId)
                .stream()
                .map(history -> {
                    HistoryDTO dto = historyMapper.toDTO(history);

                    Double avg = recipeRatingRepository
                            .findAverageRatingByRecipeId(history.getRecipe().getId());

                    dto.getRecipe().setAverageRating(avg != null ? avg : 0.0);

                    return dto;
                })
                .toList();
    }


    public Long countCookedRecipesByClient(@Valid Long clientId) {
        return historyRepository.countByClient_Id(clientId);
    }
}
