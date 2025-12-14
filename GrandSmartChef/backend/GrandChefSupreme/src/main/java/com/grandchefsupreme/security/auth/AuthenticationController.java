package com.grandchefsupreme.security.auth;


import com.grandchefsupreme.dto.*;
import com.grandchefsupreme.mapper.ClientMapper;
import com.grandchefsupreme.model.Client;
import com.grandchefsupreme.model.User;
import com.grandchefsupreme.security.service.AuthenticationService;
import com.grandchefsupreme.service.ClientService;
import com.grandchefsupreme.utils.ApiResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
    public AuthenticationResponseDTO registerStep1(
            HttpServletRequest request,
            @RequestBody @Valid RegisterStep1DTO dto
    ) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Usuario registrado correctamente"
        );

        return authenticationService.register(dto);
    }

    @PutMapping(
            value = "/register-step2",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ClientDTO completeProfile(
            HttpServletRequest request,
            @AuthenticationPrincipal User user,
            @RequestPart("profile") @Valid RegisterStep2DTO dto,
            @RequestPart(value = "photoProfile", required = false) MultipartFile photoProfile
    ) throws IOException {

        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Perfil completado correctamente"
        );

        Client updatedClient =
                clientService.updateProfile(dto, user.getId(), photoProfile);

        return clientMapper.toDTO(updatedClient);
    }


    @PostMapping("/login")
    public AuthenticationResponseDTO login(
            HttpServletRequest request,
            @RequestBody @Valid LoginRequestDTO dto
    ) {

        return authenticationService.login(dto);
    }
}
