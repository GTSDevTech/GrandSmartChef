package com.grandchefsupreme.unitary_services;

import com.grandchefsupreme.dto.RegisterStep1DTO;
import com.grandchefsupreme.exceptions.BadRequestException;
import com.grandchefsupreme.model.Client;
import com.grandchefsupreme.model.Tag;
import com.grandchefsupreme.repository.ClientRepository;
import com.grandchefsupreme.repository.TagRepository;
import com.grandchefsupreme.service.ClientService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@org.junit.jupiter.api.Tag("register")
@DisplayName("ClientService - Full Register and Update Profile")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @BeforeAll
    void setUpTags(){
        Tag tagVegetariano = new Tag();
        tagVegetariano.setName("Vegetariano");
        Tag tagVegano = new Tag();
        tagVegano.setName("Vegano");

        tagRepository.saveAll(List.of(tagVegano,tagVegetariano));

    }

    @BeforeEach
    void cleanClient(){
        clientRepository.deleteAll();

    }


    @Nested
    @DisplayName("Method createClient()")
    class CreateClient{

        @Test
        @DisplayName("Creación positiva ")
        void registerClientSuccessfully(){

            //GIVEN
            RegisterStep1DTO registerStep1DTO = new RegisterStep1DTO();
            registerStep1DTO.setUsername("GuilleTeSa");
            registerStep1DTO.setPassword("root");
            registerStep1DTO.setEmail("gts@gmail.com");

            //THEN
            Client created = clientService.createClient(registerStep1DTO);
            Client persistClient = clientRepository.findById(created.getId())
                    .orElseThrow(() -> new AssertionError("Cliente no se guardó en BBDD"));


            //WHEN
            assertAll("Create",
                    () -> assertNotNull(persistClient.getId()),
                    () -> assertEquals("GuilleTeSa", persistClient.getUsername()),
                    () -> assertEquals("gts@gmail.com", persistClient.getEmail()),
                    () -> assertTrue(created.getIsActive()),
                    () -> assertThat(registerStep1DTO.getPassword())
                            .isNotBlank()
                            .isNotEqualTo(persistClient.getPassword())
                            .as("Password debe ser válido y estar codificado"),
                    () -> assertTrue(passwordEncoder.matches(registerStep1DTO.getPassword(), created.getPassword())),
                    () -> assertEquals(persistClient.getId(), created.getId()),
                    () -> assertEquals(persistClient.getEmail(), created.getEmail())
                );

        }

        @Test
        @DisplayName("password null Caso - Negativo")
        void FailWhenPasswordNull() {
            RegisterStep1DTO registerStep1DTO = new RegisterStep1DTO();
            registerStep1DTO.setUsername("UserNullPass");
            registerStep1DTO.setPassword(null);
            registerStep1DTO.setEmail("nullpass@example.com");

           assertThrows(BadRequestException.class,
                   () -> clientService.createClient(registerStep1DTO));

            assertThat(clientRepository.count()).isZero();
        }

        @Test
        @DisplayName("password vacío, Caso - Negativo")
        void FailWhenPasswordEmpty() {
            RegisterStep1DTO registerStep1DTO = new RegisterStep1DTO();
            registerStep1DTO.setUsername("UserEmptyPass");
            registerStep1DTO.setPassword("");
            registerStep1DTO.setEmail("emptypass@example.com");

            assertThrows(BadRequestException.class,
                    () -> clientService.createClient(registerStep1DTO));

            assertThat(clientRepository.count()).isZero();
        }
    }



}
