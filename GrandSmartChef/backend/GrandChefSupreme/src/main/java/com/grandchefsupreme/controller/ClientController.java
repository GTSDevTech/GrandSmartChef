package com.grandchefsupreme.controller;

import com.grandchefsupreme.dto.ClientDTO;
import com.grandchefsupreme.dto.ClientLoginDTO;
import com.grandchefsupreme.model.User;
import com.grandchefsupreme.service.ClientService;
import com.grandchefsupreme.utils.ApiResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
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

}
