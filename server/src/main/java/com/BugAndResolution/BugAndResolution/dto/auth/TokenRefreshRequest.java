package com.BugAndResolution.BugAndResolution.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class TokenRefreshRequest {
    private String refreshToken;

    public TokenRefreshRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
