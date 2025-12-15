package com.grandchefsupreme.mapper;


import com.grandchefsupreme.dto.ShoppingListDTO;
import com.grandchefsupreme.model.ShoppingList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses = {ShoppingListIngredientMapper.class})
public interface ShoppingListMapper {

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "items", source = "items")
    ShoppingListDTO toDTO(ShoppingList shoppingList);

    ShoppingList toEntity(ShoppingListDTO shoppingListDTO);

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "items", source = "items")
    List<ShoppingListDTO> toDTO(List<ShoppingList> shoppingLists);

    List<ShoppingList> toEntity(List<ShoppingListDTO> shoppingListDTOs);



}
