package com.BugAndResolution.BugAndResolution.service.authServices;

import org.springframework.http.HttpHeaders;
import java.time.Duration;
import java.util.Optional;

import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.BugAndResolution.BugAndResolution.config.jwt.JwtService;
import com.BugAndResolution.BugAndResolution.dto.auth.AuthenticatorRequest;
import com.BugAndResolution.BugAndResolution.dto.auth.AuthenticatorResponse;
import com.BugAndResolution.BugAndResolution.dto.auth.RegisterRequest;
import com.BugAndResolution.BugAndResolution.dto.auth.TokenRefreshRequest;
import com.BugAndResolution.BugAndResolution.exception.TokenRefreshException;
import com.BugAndResolution.BugAndResolution.model.entities.RefreshToken;
import com.BugAndResolution.BugAndResolution.model.entities.User;
import com.BugAndResolution.BugAndResolution.model.enums.Role;
import com.BugAndResolution.BugAndResolution.repository.UserRepository;
import com.BugAndResolution.BugAndResolution.secuirty.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    public static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public AuthenticatorResponse register(RegisterRequest request, HttpServletResponse response) {
        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRole()));

        userRepo.save(user);

        return createAuthenticationResponse(user, response);
    }

    public AuthenticatorResponse authenticate(AuthenticatorRequest request, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        var user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return createAuthenticationResponse(user, response);
    }

    public AuthenticatorResponse refreshToken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshToken -> {
                    if (refreshTokenService.isRefreshTokenExpired(refreshToken)) {
                        refreshTokenService.deleteByUserId(refreshToken.getUser().getId());
                        throw new RuntimeException("Refresh token was expired");
                    }
                    return refreshToken;
                })
                .map(RefreshToken::getUser)
                .map(user -> {
                    UserDetailsImpl userDetails = new UserDetailsImpl(user);
                    String accessToken = jwtService.generateAccessToken(userDetails);
                    return AuthenticatorResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(requestRefreshToken)
                            .build();
                })
                .orElseThrow(() -> new TokenRefreshException("Refresh token is not in database!"));
    }

    private AuthenticatorResponse createAuthenticationResponse(User user, HttpServletResponse response) {
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        String accessToken = jwtService.generateAccessToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmail());

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken.getToken())
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/refresh-token")
                .maxAge(Duration.ofDays(7))
                .sameSite("None")
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return AuthenticatorResponse.builder()
                .id(user.getId())
                .userName(user.getName())
                .email(user.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }


    public void logout(HttpServletRequest request, HttpServletResponse response) {
        // Extract refresh token from cookie instead of request body
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        
        if (refreshToken == null || refreshToken.isEmpty()) {
            SecurityContextHolder.clearContext();
            ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                    .httpOnly(true)
                    .secure(true)
                    .path("/api/auth/refresh-token")
                    .maxAge(0)
                    .sameSite("None")
                    .build();
            response.setHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());
            return;
        }
    
        Optional<RefreshToken> tokenOptional = refreshTokenService.findByToken(refreshToken);
        if (tokenOptional.isPresent()) {
            RefreshToken token = tokenOptional.get();
            refreshTokenService.deleteByUserId(token.getUser().getId());
        }
        
        SecurityContextHolder.clearContext();
        ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/refresh-token")
                .maxAge(0)
                .sameSite("None")
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());
    }    
}