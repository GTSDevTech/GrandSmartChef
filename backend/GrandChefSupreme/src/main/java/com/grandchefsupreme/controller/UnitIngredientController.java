package com.grandchefsupreme.controller;

import com.grandchefsupreme.dto.UnitDTO;
import com.grandchefsupreme.model.Enums.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/units")
@RequiredArgsConstructor
public class UnitIngredientController {

        @GetMapping
        public List<UnitDTO> getUnits() {
            return Arrays.stream(Unit.values())
                    .map(u -> new UnitDTO(u.name(), u.getLabel()))
                    .toList();
        }

}
