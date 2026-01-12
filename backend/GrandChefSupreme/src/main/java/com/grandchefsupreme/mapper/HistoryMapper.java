package com.grandchefsupreme.mapper;

import com.grandchefsupreme.dto.HistoryDTO;
import com.grandchefsupreme.model.History;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring", uses = {ClientMapper.class, RecipeCardMapper.class })
public interface HistoryMapper {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "recipe", target = "recipe")
    @Mapping(source = "date", target = "date", qualifiedByName = "historyDateToString")
    HistoryDTO toDTO(History history);


    @InheritInverseConfiguration
    @Mapping(source = "date", target = "date", qualifiedByName = "stringToHistoryDate")
    History toEntity(HistoryDTO historyDTO);

    List<HistoryDTO> toDTO(List<History> history);

    @Mapping(target = "date", qualifiedByName = "stringToDateHistory")
    List<History> toEntity(List<HistoryDTO> historyDTO);

    @Named("historyDateToString")
    static String historyDateToString(LocalDate date) {
        return date != null
                ? date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                : null;
    }

    @Named("stringToHistoryDate")
    static LocalDate stringToHistoryDate(String date) {
        return (date != null && !date.isBlank())
                ? LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : null;
    }

}
