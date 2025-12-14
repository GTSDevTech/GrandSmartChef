package com.grandchefsupreme.mapper;

import com.grandchefsupreme.dto.HistoryDTO;
import com.grandchefsupreme.model.History;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring", uses = {ClientMapper.class, RecipeCardMapper.class})
public interface HistoryMapper {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "recipe.id", target = "recipeId")
    @Mapping(target = "date", source = "date")
    HistoryDTO toDTO(History history);


    @InheritInverseConfiguration
    History toEntity(HistoryDTO historyDTO);


    List<HistoryDTO> toDTO(List<History> history);


    List<History> toEntity(List<HistoryDTO> historyDTO);


}
