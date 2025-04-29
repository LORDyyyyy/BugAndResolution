package com.BugAndResolution.BugAndResolution.exception;

public class AccessDeniedException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
