package com.grandchefsupreme.service;

import com.grandchefsupreme.model.Client;
import com.grandchefsupreme.model.PasswordResetToken;
import com.grandchefsupreme.model.User;
import com.grandchefsupreme.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserService userService;


    public String generateToken(String email) throws Exception {
        User user = userService.getByEmail(email);

        PasswordResetToken token = new PasswordResetToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusMinutes(15));

        passwordResetTokenRepository.save(token);
        return token.getToken();

    }

    public Boolean isTokenValid(String token){
        return passwordResetTokenRepository.findByToken(token)
                .map(t -> !t.isExpired())
                .orElse(false);
    }


    public void ResetPassword(String token, String newPassword){
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token not found"));

        if(resetToken.isExpired()){
            passwordResetTokenRepository.delete(resetToken);
            throw new IllegalArgumentException("Token is expired");
        }

        Client client = (Client) resetToken.getUser();
        userService.updatePassword(client, newPassword);

        passwordResetTokenRepository.delete(resetToken);
    }
}
