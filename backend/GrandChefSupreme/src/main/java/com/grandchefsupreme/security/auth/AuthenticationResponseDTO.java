package com.grandchefsupreme.security.auth;


import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponseDTO {

    private String token;
    private String message;
}