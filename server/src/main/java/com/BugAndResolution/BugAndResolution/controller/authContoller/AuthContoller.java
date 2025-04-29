package com.BugAndResolution.BugAndResolution.controller.authContoller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.BugAndResolution.BugAndResolution.dto.auth.AuthenticatorRequest;
import com.BugAndResolution.BugAndResolution.dto.auth.AuthenticatorResponse;
import com.BugAndResolution.BugAndResolution.dto.auth.LogoutRequest;
import com.BugAndResolution.BugAndResolution.dto.auth.RegisterRequest;
import com.BugAndResolution.BugAndResolution.dto.auth.TokenRefreshRequest;
import com.BugAndResolution.BugAndResolution.service.authServices.AuthService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthContoller {

    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticatorResponse> register(@RequestBody RegisterRequest request) {
            return ResponseEntity.ok(authService.register(request));
        
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticatorResponse> authenticate(@RequestBody AuthenticatorRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticatorResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
        AuthenticatorResponse tokenResponse = authService.refreshToken(request);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequest request) {
        authService.logout(request);
        return ResponseEntity.ok().build();
    }

}
