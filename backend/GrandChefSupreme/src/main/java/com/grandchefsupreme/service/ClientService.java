package com.grandchefsupreme.service;

import com.grandchefsupreme.dto.*;
import com.grandchefsupreme.exceptions.AlreadyUserExist;
import com.grandchefsupreme.exceptions.BadRequestException;
import com.grandchefsupreme.exceptions.NotFoundException;
import com.grandchefsupreme.mapper.ClientMapper;
import com.grandchefsupreme.model.Client;
import com.grandchefsupreme.model.Tag;
import com.grandchefsupreme.model.User;
import com.grandchefsupreme.repository.ClientRepository;
import com.grandchefsupreme.repository.TagRepository;
import com.grandchefsupreme.utils.FileStorageUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {


    private final ClientRepository clientRepository;
    private final TagRepository tagRepository;
    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageUtil fileStorageUtil;

    public Client createClient(RegisterStep1DTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        if (clientDTO.getPassword() == null || clientDTO.getPassword().isEmpty()) {
            throw new BadRequestException("Password cannot be null or empty");
        }
        client.setPassword(passwordEncoder.encode(clientDTO.getPassword()));
        client.setIsActive(true);

        return clientRepository.saveAndFlush(client);

    }

    @Transactional
    public Client updatePreferences(Long userId, List<PreferenceDTO> prefs) {

        Client client = clientRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Client not found"));

        // Limpia siempre (permite desactivar todas)
        client.getPreferences().clear();

        if (prefs != null && !prefs.isEmpty()) {
            Set<Tag> managedTags = prefs.stream()
                    .map(p -> tagRepository.findById(p.getId())
                            .orElseThrow(() ->
                                    new NotFoundException("Tag not found: " + p.getId())))
                    .collect(Collectors.toSet());

            client.getPreferences().addAll(managedTags);
        }

        return clientRepository.save(client);
    }

    public Client updateProfile(RegisterStep2DTO registerStep2DTO, Long userId, MultipartFile photoFile) throws IOException {
        Client client = clientRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        Client clientMapped = clientMapper.toEntity(registerStep2DTO);

        client.setFullName(clientMapped.getFullName());
        client.setBirthdate(clientMapped.getBirthdate());
        client.setCountry(clientMapped.getCountry());

        if (clientMapped.getPreferences() != null) {

            client.getPreferences().clear();

            if (!clientMapped.getPreferences().isEmpty()) {
                Set<Tag> managedTags = clientMapped.getPreferences().stream()
                        .map(tag ->
                                tagRepository.findById(tag.getId())
                                        .orElseThrow(() ->
                                                new NotFoundException("Tag not found: " + tag.getId()))
                        )
                        .collect(Collectors.toSet());

                client.getPreferences().addAll(managedTags);
            }
        }

        if (registerStep2DTO.getEmail() != null && !registerStep2DTO.getEmail().isBlank()) {
            if (!registerStep2DTO.getEmail().equalsIgnoreCase(client.getEmail())) {
                if (clientRepository.existsByEmail(registerStep2DTO.getEmail())) {
                    throw new BadRequestException("El email ya está en uso");
                }
                client.setEmail(registerStep2DTO.getEmail());
            }
        }

        if (photoFile != null && !photoFile.isEmpty()) {
            String photoPath = fileStorageUtil.saveProfilePhoto(photoFile);
            client.setPhotoProfile(photoPath);
        } else if (client.getPhotoProfile() == null || client.getPhotoProfile().isEmpty()) {
            String DEFAULT_PHOTO = "uploads/profile/default_profile_image.png";
            client.setPhotoProfile(DEFAULT_PHOTO);
        }
        return clientRepository.saveAndFlush(client);


    }

    public void getClientByEmail(String email) {

        if (clientRepository.existsByEmail(email)) {
            throw new AlreadyUserExist("El cliente ya existe");
        }
    }


    public ClientLoginDTO getClient(User user) {
        Client client = clientRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        return clientMapper.toLoginDTO(client);
    }


    public ClientDTO getClientProfile(User user) {
        Client client = clientRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        return clientMapper.toDTO(client);
    }

}
