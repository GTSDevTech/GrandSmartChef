package com.grandchefsupreme.mapper;

import com.grandchefsupreme.dto.UserDTO;
import com.grandchefsupreme.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    List<UserDTO> toDTO(List<User> listEntity);



}
