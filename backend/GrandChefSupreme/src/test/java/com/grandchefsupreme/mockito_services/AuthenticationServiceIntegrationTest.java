package com.grandchefsupreme.mockito_services;

import com.grandchefsupreme.dto.RegisterStep1DTO;
import com.grandchefsupreme.exceptions.UnauthorizedException;
import com.grandchefsupreme.model.Client;
import com.grandchefsupreme.security.service.AuthenticationService;
import com.grandchefsupreme.security.service.JwtService;
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

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceIntegrationTest {

    @Mock
    public ClientService clientService;

    @InjectMocks
    public AuthenticationService authenticationService;


    @Mock
    public JwtService jwtService;



    @Nested
    @DisplayName("Authentication - Positive Cases")
    class ClientPositiveCases{

        @Test
        @DisplayName("Register Client Successfully Return Token")
        void createClientSuccessfully(){

           RegisterStep1DTO registerStep1DTO = new RegisterStep1DTO();
           registerStep1DTO.setEmail("email");
           registerStep1DTO.setPassword("password");
           registerStep1DTO.setUsername("username");


            //GIVEN
            Mockito.doNothing().when(clientService).getClientByEmail(Mockito.anyString());
            Mockito.when(clientService.createClient(Mockito.any(RegisterStep1DTO.class))).thenReturn(Mockito.mock(Client.class));



            //THEN
            authenticationService.register(registerStep1DTO);

            //WHEN

            Mockito.verify(jwtService, Mockito.times(1)).generateToken(Mockito.any());
            Mockito.verify(clientService).createClient(Mockito.any(RegisterStep1DTO.class));
            Mockito.verify(jwtService).generateToken(Mockito.any(UserDetails.class));
        }


    }


    @Nested
    @DisplayName("Authentication - Negative Cases")
    class ClientNegativeCases{

        @Test
        @DisplayName("Register Client with Null DTO and Errors - Negative Case")
        void createClientWithErrors() {
            //GIVEN

            //THEN
            assertThrows(UnauthorizedException.class,
                    () -> authenticationService.register(null));
            //WHEN
            Mockito.verifyNoInteractions(clientService);
            Mockito.verifyNoInteractions(jwtService);


        }

        @Test
        @DisplayName("Email already exists - Negative Case")
        void createClientWithEmailExisting() {

            RegisterStep1DTO dto = new RegisterStep1DTO();
            dto.setEmail("email");
            dto.setUsername("user");
            dto.setPassword("pass");
            //GIVEN
            Mockito.doThrow(new RuntimeException("Email exists"))
                    .when(clientService)
                    .getClientByEmail(Mockito.anyString());
            //THEN
            assertThrows(RuntimeException.class,
                    () -> authenticationService.register(dto));
            //WHEN
            Mockito.verify(clientService, Mockito.never())
                    .createClient(Mockito.any());

            Mockito.verify(jwtService, Mockito.never())
                    .generateToken(Mockito.any());


        }

    }





}
