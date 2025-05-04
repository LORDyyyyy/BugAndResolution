package com.BugAndResolution.BugAndResolution.dto.auth;

import lombok.Data;

@Data
public class TokenRefreshRequest {
    private String refreshToken;

    public TokenRefreshRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
