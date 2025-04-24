package com.BugAndResolution.BugAndResolution.dto;

import com.BugAndResolution.BugAndResolution.model.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BugDTO {

    private Long id;
    private String title;
    private String description;
    private String severity;
    private Integer developerId;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
