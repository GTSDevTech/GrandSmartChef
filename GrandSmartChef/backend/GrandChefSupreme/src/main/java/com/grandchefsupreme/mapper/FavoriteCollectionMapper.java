package com.grandchefsupreme.mapper;

import com.grandchefsupreme.dto.FavoriteCollectionDTO;
import com.grandchefsupreme.model.FavoriteCollection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RecipeCardMapper.class})
public interface FavoriteCollectionMapper {

    @Mapping(source = "client.id", target = "clientId")
    FavoriteCollectionDTO toDTO(FavoriteCollection entity);

    @Mapping(target = "client", ignore = true)
    @Mapping(source = "recipes", target = "recipes")
    FavoriteCollection toEntity(FavoriteCollectionDTO dto);

    @Mapping(source = "client.id", target = "clientId")
    List<FavoriteCollectionDTO> toDTO(List<FavoriteCollection> list);

    @Mapping(source = "clientId", target = "client.id")
    @Mapping(source = "recipes", target = "recipes")
    List<FavoriteCollection> toEntity(List<FavoriteCollectionDTO> list);

}
