package com.BugAndResolution.BugAndResolution.dto.bugs;

public class BugRequestDTO {

    private String title;
    private String description;
    private String severity;
    private Long submittedById;
    private Long developerId;

    public BugRequestDTO() {}

    public BugRequestDTO(String title, String description, String severity, Long submittedById) {
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.submittedById = submittedById;
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

    public Long getSubmittedById() {
        return submittedById;
    }

    public void setSubmittedById(Long submittedById) {
        this.submittedById = submittedById;
    }

    public Long getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(Long developerId) {
        this.developerId = developerId;
    }
}
