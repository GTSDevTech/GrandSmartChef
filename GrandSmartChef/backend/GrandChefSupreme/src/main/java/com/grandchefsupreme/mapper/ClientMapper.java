package com.grandchefsupreme.mapper;


import com.grandchefsupreme.dto.ClientDTO;
import com.grandchefsupreme.dto.ClientLoginDTO;
import com.grandchefsupreme.dto.RegisterStep1DTO;
import com.grandchefsupreme.dto.RegisterStep2DTO;
import com.grandchefsupreme.model.Client;
import org.mapstruct.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ClientMapper {


    @Mapping(target = "birthdate", qualifiedByName = "dateToString")
    ClientDTO toDTO(Client client);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    ClientLoginDTO toLoginDTO(Client client);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fullName", ignore = true)
    @Mapping(target = "birthdate", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "photoProfile", ignore = true)
    @Mapping(target = "preferences", ignore = true)
    Client toEntity(RegisterStep1DTO dto);


    @Mapping(target = "birthdate", qualifiedByName = "stringToDate")
    Client toEntity(RegisterStep2DTO dto);


    @Named("dateToString")
    default String dateToString(LocalDate date) {
        return date != null
                ? date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                : null;
    }

    @Named("stringToDate")
    default LocalDate stringToDate(String date) {
        return (date != null && !date.isBlank())
                ? LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : null;
    }
}

