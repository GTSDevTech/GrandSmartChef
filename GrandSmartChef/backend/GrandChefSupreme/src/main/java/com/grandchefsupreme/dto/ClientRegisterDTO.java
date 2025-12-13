package com.grandchefsupreme.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;


@Data
public class ClientRegisterDTO {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String birthdate;
    private String country;
    private String photoProfile;

    private Set<TagDTO> preferences;
}
