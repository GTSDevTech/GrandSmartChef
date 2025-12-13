package com.grandchefsupreme.controller;

import com.grandchefsupreme.dto.HistoryDTO;
import com.grandchefsupreme.service.HistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @PostMapping("/create")
    public ResponseEntity<HistoryDTO> createHistory(@Valid @RequestBody HistoryDTO historyDTO) {
        HistoryDTO history = historyService.createHistory(historyDTO);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/recipes/last7days")
    public ResponseEntity<List<HistoryDTO>> getRecipesLast7daysByClient(@Valid
            @RequestParam Long clientId,
            @RequestParam LocalDate date) {
        List<HistoryDTO> histories = historyService.getRecipesLast7daysByClient(date, clientId);
        return ResponseEntity.ok(histories);
    }

}