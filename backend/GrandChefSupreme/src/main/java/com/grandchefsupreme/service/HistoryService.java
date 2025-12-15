package com.grandchefsupreme.service;

import com.grandchefsupreme.dto.HistoryDTO;
import com.grandchefsupreme.mapper.HistoryMapper;
import com.grandchefsupreme.model.Client;
import com.grandchefsupreme.model.History;
import com.grandchefsupreme.model.Recipe;
import com.grandchefsupreme.repository.ClientRepository;
import com.grandchefsupreme.repository.HistoryRepository;
import com.grandchefsupreme.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final HistoryMapper historyMapper;

    public HistoryDTO createHistory(HistoryDTO historyDTO){
        History history =  historyMapper.toEntity(historyDTO);
        return historyMapper.toDTO(historyRepository.save(history));
    }

    public List<HistoryDTO> getRecipesLast7daysByClient(LocalDate date, Long clientId) {
        LocalDate sevenDaysAgo = date.minusDays(7);
        return historyMapper.toDTO(historyRepository.findAllFromLast7Days(sevenDaysAgo, date, clientId));
    }


}
