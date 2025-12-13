package com.grandchefsupreme.mapper;


import com.grandchefsupreme.dto.ClientDTO;
import com.grandchefsupreme.dto.ClientRegisterDTO;
import com.grandchefsupreme.dto.RecipeCardDTO;
import com.grandchefsupreme.model.Client;
import com.grandchefsupreme.model.Recipe;
import org.mapstruct.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ClientMapper {

    @InheritConfiguration(name = "toDTO")
    @Mapping(target = "birthdate", qualifiedByName = "converterDateToString")
    ClientDTO toDTO(Client client);


    @InheritConfiguration(name = "toEntity")
    @Mapping(target = "birthdate", qualifiedByName = "converterStringToDate")
    Client toEntity(ClientDTO clientDTO);

    @InheritConfiguration(name = "toDTO")
    @Mapping(target = "birthdate", qualifiedByName = "converterStringToDate")
    @Mapping(target = "password", source = "password")
    Client toEntity(ClientRegisterDTO clientRegisterDTO);

    @InheritConfiguration(name = "toDTO")
    @Mapping(target = "birthdate", qualifiedByName = "converterDateToString")
    ClientRegisterDTO toRegisterDTO(Client client);

    List<ClientDTO> toDTO(List<Client> listEntity);
    List<Client> toEntity(List<ClientDTO> listDTOs);

    @Named(value ="converterDateToString")
    default String converterDateToString(LocalDate fecha){
        if (fecha == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fecha.format(formatter);
    }

    @Named(value ="converterStringToDate")
    default LocalDate converterStringToDate(String fecha){
        if (fecha == null || fecha.isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(fecha,formatter);
    }



}
