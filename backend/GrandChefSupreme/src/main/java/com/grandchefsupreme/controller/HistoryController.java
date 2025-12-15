package com.grandchefsupreme.controller;

import com.grandchefsupreme.dto.HistoryDTO;
import com.grandchefsupreme.service.HistoryService;
import com.grandchefsupreme.utils.ApiResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @PostMapping("/create")
    public HistoryDTO createHistory(
            HttpServletRequest request,
            @Valid @RequestBody HistoryDTO historyDTO
    ) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Historial creado correctamente"
        );

        return historyService.createHistory(historyDTO);
    }

    @GetMapping("/recipes/last7days")
    public List<HistoryDTO> getRecipesLast7daysByClient(
            HttpServletRequest request,
            @RequestParam @Valid Long clientId,
            @RequestParam LocalDate date
    ) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Historial de los últimos 7 días obtenido correctamente"
        );

        return historyService.getRecipesLast7daysByClient(date, clientId);
    }
}
