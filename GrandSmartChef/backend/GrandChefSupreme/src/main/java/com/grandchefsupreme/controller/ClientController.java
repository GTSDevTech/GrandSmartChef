package com.grandchefsupreme.controller;


import com.grandchefsupreme.dto.ClientDTO;
import com.grandchefsupreme.dto.ClientRegisterDTO;
import com.grandchefsupreme.model.Client;
import com.grandchefsupreme.model.User;
import com.grandchefsupreme.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/me")
    public ResponseEntity<ClientDTO> getClient(@AuthenticationPrincipal User user) {
        ClientDTO clientDTO = clientService.getClient(user);
        return ResponseEntity.ok(clientDTO);
    }


}
