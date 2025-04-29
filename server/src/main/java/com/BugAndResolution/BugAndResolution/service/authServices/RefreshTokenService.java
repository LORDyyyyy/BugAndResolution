package com.BugAndResolution.BugAndResolution.service.authServices;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.BugAndResolution.BugAndResolution.exception.ResourceNotFoundException;
import com.BugAndResolution.BugAndResolution.model.entities.RefreshToken;
import com.BugAndResolution.BugAndResolution.model.entities.User;
import com.BugAndResolution.BugAndResolution.repository.RefreshTokenRepository;
import com.BugAndResolution.BugAndResolution.repository.UserRepository;

@Service
public class RefreshTokenService {

    @Value("${app.jwt.refresh-token-expiration-ms:604800000}")
    private long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userEmail));
    
        Instant expiryInstant = Instant.now().plusMillis(refreshTokenDurationMs);
    
        return refreshTokenRepository.findByUserId(user.getId())
                .map(existingToken -> {
                    existingToken.setExpiryDate(expiryInstant);
                    existingToken.setToken(UUID.randomUUID().toString());
                    return refreshTokenRepository.save(existingToken);
                })
                .orElseGet(() -> {
                    RefreshToken newToken = new RefreshToken();
                    newToken.setUser(user);
                    newToken.setExpiryDate(expiryInstant);
                    newToken.setToken(UUID.randomUUID().toString());
                    return refreshTokenRepository.save(newToken);
                });
    }

    @Transactional
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }
    
    public boolean isRefreshTokenExpired(RefreshToken token) {
        return token.getExpiryDate().isBefore(Instant.now());
    }
}