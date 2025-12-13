package com.grandchefsupreme.security.service;

import com.grandchefsupreme.dto.ClientRegisterDTO;
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


    //Register
    public AuthenticationResponseDTO register(ClientRegisterDTO clientDTO) {
        Client savedClient = clientService.createClient(clientDTO);
        String token = jwtService.generateToken(savedClient);
        return AuthenticationResponseDTO.builder()
                .token(token)
                .message("Register success")
                .build();
    }

    //Login
    public AuthenticationResponseDTO login(ClientRegisterDTO clientDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(clientDTO.getUsername(), clientDTO.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new UnauthorizedException("Invalid Credentials");
        }

        User user = (User) userService.loadUserByUsername(clientDTO.getUsername());
        String token = jwtService.generateToken(user);

        return AuthenticationResponseDTO.builder()
                .token(token)
                .message("Login successs")
                .build();
    }

    public boolean verifyPassword(ClientRegisterDTO clientDTO) {
        return userService.validateCredentials(clientDTO);
    }

    public void updatePassword(User user, String newPassword) {
        userService.updatePassword(user, newPassword);
    }

}
