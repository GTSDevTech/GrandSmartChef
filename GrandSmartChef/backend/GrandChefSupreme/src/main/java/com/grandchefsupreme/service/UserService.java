package com.grandchefsupreme.service;

import com.grandchefsupreme.dto.ClientRegisterDTO;
import com.grandchefsupreme.dto.UserDTO;
import com.grandchefsupreme.mapper.ClientMapper;
import com.grandchefsupreme.mapper.UserMapper;
import com.grandchefsupreme.model.User;
import com.grandchefsupreme.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final ClientMapper clientMapper;

    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findTopByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserDTO getByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findTopByUsername(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }else{
            return  userMapper.toDTO(user);
        }
    }

    public User getByEmail(String email) throws Exception {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public boolean validateCredentials(ClientRegisterDTO clientDTO) {

        User user = userRepository.findTopByUsername(clientDTO.getUsername()).orElse(null);

        return user != null && passwordEncoder.matches(clientDTO.getPassword(), user.getPassword());
    }


    public void updatePassword(User user, String rawPassword){
        user.setPassword(passwordEncoder.encode(rawPassword));
        userRepository.saveAndFlush(user);
    }



}
