package com.grandchefsupreme.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class RegisterStep2DTO {

    @NotBlank(message = "Nombre obligatorio")
    private String fullName;

    @Pattern(
            regexp = "\\d{4}-\\d{2}-\\d{2}",
            message = "La fecha debe tener formato yyyy-MM-dd"
    )
    private String birthdate;


    private String country;

    @Email(message = "Email inválido")
    @NotBlank
    private String email;

    private List<TagDTO> preferences;
}
