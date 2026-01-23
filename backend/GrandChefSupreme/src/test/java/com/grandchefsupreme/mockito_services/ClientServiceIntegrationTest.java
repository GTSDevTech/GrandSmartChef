package com.grandchefsupreme.mockito_services;

import com.grandchefsupreme.dto.RegisterStep1DTO;
import com.grandchefsupreme.exceptions.BadRequestException;
import com.grandchefsupreme.mapper.ClientMapper;
import com.grandchefsupreme.model.Client;
import com.grandchefsupreme.repository.ClientRepository;
import com.grandchefsupreme.service.ClientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ClientServiceIntegrationTest {

    @InjectMocks
    public ClientService clientService;

    @Mock
    public ClientRepository clientRepository;

    @Mock
    public ClientMapper clientMapper;

    @Mock
    public PasswordEncoder passwordEncoder;


    @Nested
    @DisplayName("Create Client Successfully - Positive Case")
    class CreateClientSuccessfully {

        @Test
        @DisplayName("Create Client Return Client Entity")
        void createClientSuccessfully() {

            RegisterStep1DTO registerStep1DTO = new RegisterStep1DTO();
            registerStep1DTO.setEmail("gts@gmail.com");
            registerStep1DTO.setUsername("gts");
            registerStep1DTO.setPassword("gts123");


            //GIVEN
            Mockito.when(clientRepository.saveAndFlush(Mockito.any(Client.class))).thenReturn(Mockito.mock(Client.class));
            Mockito.when(clientMapper.toEntity(registerStep1DTO)).thenReturn(Mockito.mock(Client.class));
            Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn(Mockito.anyString());

            //THEN
            clientService.createClient(registerStep1DTO);

            //WHEN
            Mockito.verify(clientRepository, Mockito.times(1)).saveAndFlush(Mockito.any(Client.class));
            Mockito.verify(clientMapper, Mockito.times(1)).toEntity(Mockito.any(RegisterStep1DTO.class));
            Mockito.verify(passwordEncoder, Mockito.times(1)).encode(Mockito.anyString());


        }
    }

    @Nested
    @DisplayName("Create Client - Negative Cases")
    class ClientNegativeCases {

        @Test
        @DisplayName("Create Client with null DTO and Errors - Negative Case")
        void createClientWithErrors() {


            assertThrows(IllegalArgumentException.class, () -> {
                clientService.createClient(null);
            });

            Mockito.verify(clientRepository, Mockito.never()).saveAndFlush(Mockito.any(Client.class));
            Mockito.verify(clientMapper, Mockito.never()).toEntity(Mockito.any(RegisterStep1DTO.class));
            Mockito.verify(passwordEncoder, Mockito.never()).encode(Mockito.anyString());

        }


        @Test
        @DisplayName("Create Client with Password Empty - Negative Case")
        void createClientWithPasswordEmpty() {

            RegisterStep1DTO registerStep1DTO = new RegisterStep1DTO();
            registerStep1DTO.setPassword("");

            assertThrows(BadRequestException.class, () -> {
                clientService.createClient(registerStep1DTO);
            });

            Mockito.verifyNoInteractions(clientRepository);
            Mockito.verifyNoInteractions(passwordEncoder);
            Mockito.verifyNoInteractions(clientMapper);


        }
    }


}
