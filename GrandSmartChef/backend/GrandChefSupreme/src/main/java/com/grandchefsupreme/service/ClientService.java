package com.grandchefsupreme.service;

import com.grandchefsupreme.dto.ClientDTO;
import com.grandchefsupreme.dto.ClientRegisterDTO;
import com.grandchefsupreme.exceptions.BadRequestException;
import com.grandchefsupreme.exceptions.NotFoundException;
import com.grandchefsupreme.mapper.ClientMapper;
import com.grandchefsupreme.model.Client;
import com.grandchefsupreme.model.Tag;
import com.grandchefsupreme.model.User;
import com.grandchefsupreme.repository.ClientRepository;
import com.grandchefsupreme.utils.FileStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ClientService {


    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageUtil fileStorageUtil;

    public Client createClient(ClientRegisterDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        if (clientDTO.getPassword() == null || clientDTO.getPassword().isEmpty()) {
            throw new BadRequestException("Password cannot be null or empty");
        }
        client.setPassword(passwordEncoder.encode(clientDTO.getPassword()));
        client.setIsActive(true);

        return clientRepository.saveAndFlush(client);

    }

    public Client updateProfile(ClientRegisterDTO clientRegisterDTO, Long userId, MultipartFile photoFile) throws IOException {
        Client client = clientRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Client not found"));

        Client clientMapped = clientMapper.toEntity(clientRegisterDTO);

        client.setFullName(clientMapped.getFullName());
        client.setBirthdate(clientMapped.getBirthdate());
        client.setCountry(clientMapped.getCountry());

        if (clientMapped.getPreferences() != null && !clientMapped.getPreferences().isEmpty()) {
            client.setPreferences(clientMapped.getPreferences());
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

    public ClientDTO getClient(User user) {
        Client client = clientRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("Client not found"));
        return clientMapper.toDTO(client);
    }

}
