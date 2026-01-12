package com.grandchefsupreme.controller;

import com.grandchefsupreme.dto.ClientDTO;
import com.grandchefsupreme.dto.ClientLoginDTO;
import com.grandchefsupreme.dto.UpdatePreferencesDTO;
import com.grandchefsupreme.model.Client;
import com.grandchefsupreme.model.User;
import com.grandchefsupreme.service.ClientService;
import com.grandchefsupreme.utils.ApiResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/me")
    public ClientLoginDTO getClient(
            HttpServletRequest request,
            @AuthenticationPrincipal User user
    ) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Cliente recuperado correctamente"
        );

        return clientService.getClient(user);
    }

    @GetMapping("/profile")
    public ClientDTO getClientProfile(
            HttpServletRequest request,
            @AuthenticationPrincipal User user
    ) {
        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Perfil recuperado correctamente"
        );

        return clientService.getClientProfile(user);
    }

    @PatchMapping("/preferences")
    public ClientDTO updatePreferences(
            HttpServletRequest request,
            @AuthenticationPrincipal User user,
            @RequestBody @Valid UpdatePreferencesDTO dto
    ) {

        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Preferencias actualizadas correctamente"
        );

        Client updatedClient =
                clientService.updatePreferences(user.getId(), dto.getPreferences());

        return clientService.getClientProfile(updatedClient);
    }

}
