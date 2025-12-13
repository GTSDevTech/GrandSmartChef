package com.grandchefsupreme.security.auth;


import com.grandchefsupreme.dto.ClientDTO;
import com.grandchefsupreme.dto.ClientRegisterDTO;
import com.grandchefsupreme.mapper.ClientMapper;
import com.grandchefsupreme.model.Client;
import com.grandchefsupreme.model.User;
import com.grandchefsupreme.security.service.AuthenticationService;
import com.grandchefsupreme.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final ClientMapper clientMapper;
    private final ClientService clientService;

    @PostMapping("/register-step1")
    public ResponseEntity<AuthenticationResponseDTO> register(
            @RequestBody ClientRegisterDTO request
    ) {
        AuthenticationResponseDTO responseDTO =
                authenticationService.register(request);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/register-step2")
    public ResponseEntity<ClientDTO> completeProfile(
            @AuthenticationPrincipal User user,
            @RequestBody ClientRegisterDTO clientDTO,
            @RequestPart(value = "photoProfile", required = false) MultipartFile photoProfileFile
    ) throws IOException {

        Client updatedClient =
                clientService.updateProfile(clientDTO, user.getId(), photoProfileFile);

        return ResponseEntity.ok(clientMapper.toDTO(updatedClient));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(
            @RequestBody ClientRegisterDTO clientRegisterDTO
    ) {
        AuthenticationResponseDTO responseDTO =
                authenticationService.login(clientRegisterDTO);

        return ResponseEntity.ok(responseDTO);
    }
}
