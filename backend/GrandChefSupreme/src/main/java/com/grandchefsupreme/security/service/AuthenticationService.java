package com.grandchefsupreme.security.service;

import com.grandchefsupreme.dto.RegisterStep1DTO;
import com.grandchefsupreme.dto.LoginRequestDTO;
import com.grandchefsupreme.exceptions.UnauthorizedException;
import com.grandchefsupreme.mapper.ClientMapper;
import com.grandchefsupreme.model.Client;
import com.grandchefsupreme.model.User;
import com.grandchefsupreme.security.auth.AuthenticationResponseDTO;
import com.grandchefsupreme.service.ClientService;
import com.grandchefsupreme.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final ClientService clientService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;



    public AuthenticationResponseDTO register(RegisterStep1DTO clientDTO) {

        if(clientDTO == null)
            throw new UnauthorizedException("El cliente no puede ser nulo");

        clientService.getClientByEmail(clientDTO.getEmail());
        clientService.getClientByUsername(clientDTO.getUsername());
        Client newClient = clientService.createClient(clientDTO);
        String token = jwtService.generateToken(newClient);
        return AuthenticationResponseDTO.builder()
                .token(token)
                .message("Registro correcto")
                .build();
    }

    public AuthenticationResponseDTO login(LoginRequestDTO clientDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            clientDTO.getUsername(),
                            clientDTO.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new UnauthorizedException("Credenciales incorrectas");
        }

        User user = (User) userService.loadUserByUsername(clientDTO.getUsername());
        String token = jwtService.generateToken(user);

        return AuthenticationResponseDTO.builder()
                .token(token)
                .message("Login correcto")
                .build();
    }

    public boolean verifyPassword(RegisterStep1DTO clientDTO) {
        return userService.validateCredentials(clientDTO);
    }

    public void updatePassword(User user, String newPassword) {
        userService.updatePassword(user, newPassword);
    }

}
