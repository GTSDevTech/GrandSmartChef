package com.grandchefsupreme.services;


import com.grandchefsupreme.dto.RegisterStep1DTO;
import com.grandchefsupreme.exceptions.AlreadyUserExist;

import com.grandchefsupreme.model.Client;
import com.grandchefsupreme.repository.ClientRepository;
import com.grandchefsupreme.security.auth.AuthenticationResponseDTO;
import com.grandchefsupreme.security.service.AuthenticationService;
import com.grandchefsupreme.security.service.JwtService;
import com.grandchefsupreme.service.ClientService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("auth")
@DisplayName("AuthenticationService - Register (step1) + login")
public class AuthenticationServiceTest {


    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private JwtService jwtService;

    @BeforeEach
    void cleanDb(){
        clientRepository.deleteAll();

    }



    @Nested
    @DisplayName("Register Step 1")
    class RegisterStep1Tests {

        @Test
        @DisplayName("Register Step 1 - Casos Positivos")
        void registerNewClientAndReturnTokenSuccessfully(){

            //Given
            RegisterStep1DTO registerStep1DTO = new RegisterStep1DTO();
            registerStep1DTO.setUsername("GuilleTeSa");
            registerStep1DTO.setPassword("root");
            registerStep1DTO.setEmail("gts@gmail.com");

            //Then
            AuthenticationResponseDTO auth = authenticationService.register(registerStep1DTO);
            Client client = clientRepository.findAll().stream()
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("No se creó el cliente en BBDD"));

            //When
            assertAll("Register Step 1",
                    () -> assertThat(auth).isNotNull(),
                    () -> assertThat(auth.getToken()).isNotBlank(),
                    () -> assertNotNull(client.getUsername()),
                    () -> assertThat(registerStep1DTO.getPassword())
                            .isNotBlank()
                            .isNotEqualTo(client.getPassword())
                            .as("Password debe ser válido y estar codificado"),
                    () -> assertNotNull(client.getEmail()),
                    () -> assertEquals("GuilleTeSa", client.getUsername()),
                    () -> assertEquals("root",registerStep1DTO.getPassword()),
                    () -> assertEquals("gts@gmail.com", client.getEmail()),
                    () -> assertTrue(client.getIsActive()),
                    () -> assertEquals("Registro correcto", auth.getMessage())
            );

        }
    }

    @Nested
    @DisplayName("Register Step 1 - Casos Negativos")
    class RegisterStep1NegativeTest {

        @Test
        @DisplayName("Email ya existe")
        void FailWhenEmailAlreadyExist() {
            RegisterStep1DTO registerStep1DTO = new RegisterStep1DTO();
            registerStep1DTO.setUsername("GuilleTeSa");
            registerStep1DTO.setPassword("root");
            registerStep1DTO.setEmail("dup@gmail.com");

            authenticationService.register(registerStep1DTO);

            assertThrows(AlreadyUserExist.class,
                    () -> authenticationService.register(registerStep1DTO),
                    "El usuario ya está registrado");
            assertEquals(1,
                    clientRepository.count(),
                    "Solo debe haber un cliente en BBDD con ese email");

        }

        @Test
        @DisplayName("Username ya existe")
        void FailWhenUsernameAlreadyExist() {
            RegisterStep1DTO registerStep1DTO = new RegisterStep1DTO();
            registerStep1DTO.setUsername("GuilleTeSa");
            registerStep1DTO.setPassword("root");
            registerStep1DTO.setEmail("gts@gmail.com");

            authenticationService.register(registerStep1DTO);

            assertThrows(AlreadyUserExist.class,
                    () -> authenticationService.register(registerStep1DTO),
                    "El nombre de usuario ya está registrado");
            assertEquals(1,
                    clientRepository.count(),
                    "Solo debe existir un cliente en BBDD con ese username");
        }



    }
}
