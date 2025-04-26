package com.BugAndResolution.BugAndResolution.dto.bugs;

public class BugRequestDTO {

    private String title;
    private String description;
    private String severity;
    private Integer submittedById;
    private Integer developerId;

    public BugRequestDTO() {}

    public BugRequestDTO(String title, String description, String severity, Integer submittedById) {
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

    public Integer getSubmittedById() {
        return submittedById;
    }

    public void setSubmittedById(Integer submittedById) {
        this.submittedById = submittedById;
    }

    public Integer getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(Integer developerId) {
        this.developerId = developerId;
    }
}
