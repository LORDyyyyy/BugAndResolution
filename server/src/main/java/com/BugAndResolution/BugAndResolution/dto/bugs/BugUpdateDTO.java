package com.BugAndResolution.BugAndResolution.dto.bugs;


import com.BugAndResolution.BugAndResolution.model.enums.Status;

public class BugUpdateDTO {
    private Long bugId;
    private Status status;
    private Integer developerId;
    private Integer submittedById;
    private String title;
    private String description;
    private String severity;

    public BugUpdateDTO() {}


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

    public Long getBugId() {
        return bugId;
    }

    public void setBugId(Long bugId) {
        this.bugId = bugId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(Integer developerId) {
        this.developerId = developerId;
    }
}
