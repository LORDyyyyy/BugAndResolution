package com.BugAndResolution.BugAndResolution.dto.user;

import jakarta.validation.constraints.NotNull;

public class DeveloperAssignmentDTO {
    @NotNull(message = "Developer ID cannot be null")
    private Long developerId;

    public Long getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(Long developerId) {
        this.developerId = developerId;
    }
}
