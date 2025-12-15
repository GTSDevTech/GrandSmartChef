package com.grandchefsupreme.service;

import com.grandchefsupreme.dto.ClientDTO;
import com.grandchefsupreme.dto.ClientLoginDTO;
import com.grandchefsupreme.dto.RegisterStep1DTO;
import com.grandchefsupreme.dto.RegisterStep2DTO;
import com.grandchefsupreme.exceptions.AlreadyUserExist;
import com.grandchefsupreme.exceptions.BadRequestException;
import com.grandchefsupreme.exceptions.NotFoundException;
import com.grandchefsupreme.mapper.ClientMapper;
import com.grandchefsupreme.model.Client;
import com.grandchefsupreme.model.User;
import com.grandchefsupreme.repository.ClientRepository;
import com.grandchefsupreme.utils.FileStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ClientService {


    private final ClientRepository clientRepository;
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

    public Client updateProfile(RegisterStep2DTO registerStep2DTO, Long userId, MultipartFile photoFile) throws IOException {
        Client client = clientRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Client not found"));

        Client clientMapped = clientMapper.toEntity(registerStep2DTO);

        client.setFullName(clientMapped.getFullName());
        client.setBirthdate(clientMapped.getBirthdate());
        client.setCountry(clientMapped.getCountry());

        if (clientMapped.getPreferences() != null && !clientMapped.getPreferences().isEmpty()) {
            client.setPreferences(clientMapped.getPreferences());
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
                .orElseThrow(() -> new NotFoundException("Client not found"));
        return clientMapper.toLoginDTO(client);
    }


    public ClientDTO getClientProfile(User user) {
        Client client = clientRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("Client not found"));
        return clientMapper.toDTO(client);
    }

}
