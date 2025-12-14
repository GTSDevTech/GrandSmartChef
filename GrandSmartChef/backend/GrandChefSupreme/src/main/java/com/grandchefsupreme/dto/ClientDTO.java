package com.grandchefsupreme.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
public class ClientDTO extends UserDTO{
    private Long id;
    private String fullName;
    private String email;
    private String birthdate;
    private String country;
    private String photoProfile;
    private List<TagDTO> preferences;

}
