package com.BugAndResolution.BugAndResolution.dto.bugs;

import jakarta.validation.constraints.NotEmpty;

public class BugRequestDTO {

    @NotEmpty(message = "Title cannot be null or empty")
    private String title;
    @NotEmpty(message = "Description cannot be null or empty")
    private String description;
    @NotEmpty(message = "Severity cannot be null or empty")
    private String severity;
  
    private Long developerId;

    public BugRequestDTO() {}

    public BugRequestDTO(String title, String description, String severity) {
        this.title = title;
        this.description = description;
        this.severity = severity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Long getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(Long developerId) {
        this.developerId = developerId;
    }
}
