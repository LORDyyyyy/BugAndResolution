package com.BugAndResolution.BugAndResolution.dto.comment;

import java.time.LocalDateTime;

public class CommentResponseDTO {

    private Long id;
    private String content;
    private Long bugId;
    private Long userId;
    private LocalDateTime createdAt;   

    public CommentResponseDTO() {
    }

    public CommentResponseDTO(Long id, String content, Long bugId, Long userId, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.bugId = bugId;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getBugId() {
        return bugId;
    }

    public void setBugId(Long bugId) {
        this.bugId = bugId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
