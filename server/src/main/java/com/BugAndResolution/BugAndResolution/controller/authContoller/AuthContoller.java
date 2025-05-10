package com.BugAndResolution.BugAndResolution.controller.authContoller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.BugAndResolution.BugAndResolution.dto.auth.AuthenticatorRequest;
import com.BugAndResolution.BugAndResolution.dto.auth.AuthenticatorResponse;
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
    public ResponseEntity<AuthenticatorResponse> register(@RequestBody RegisterRequest request,
            HttpServletResponse response) {
        return ResponseEntity.ok(authService.register(request, response));

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticatorResponse> authenticate(@RequestBody AuthenticatorRequest request,
            HttpServletResponse response) {
        return ResponseEntity.ok(authService.authenticate(request, response));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticatorResponse> refreshToken(@CookieValue String refreshToken) {
        var x = new TokenRefreshRequest(refreshToken);

        AuthenticatorResponse tokenResponse = authService.refreshToken(x);

        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
        return ResponseEntity.ok().build();
    }

}
