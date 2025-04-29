package com.BugAndResolution.BugAndResolution.exception;

public class TokenRefreshException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TokenRefreshException(String token, String message) {
        super(String.format("Failed for Token [%s]: %s", token, message));
    }

    public TokenRefreshException(String message) {
        super(message);
    }

}
