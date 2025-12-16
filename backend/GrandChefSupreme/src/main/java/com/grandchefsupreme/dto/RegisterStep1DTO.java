package com.grandchefsupreme.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import java.util.List;


@Data
public class RegisterStep1DTO {

    @NotBlank(message = "Username obligatorio")
    private String username;
    @NotBlank(message = "Password obligatorio")
    private String password;

    @Email(message = "Email incorrecto")
    @NotBlank(message = "Email obligatorio")
    private String email;

}
