package com.BugAndResolution.BugAndResolution.service.bugServices;

import com.BugAndResolution.BugAndResolution.dto.BugDTO;
import com.BugAndResolution.BugAndResolution.model.entities.Bug;

import java.util.List;
import java.util.Optional;

public interface BugService {
    List<Bug> getAllBugs();
    Optional<Bug>getBugById(Long id);
    Bug postBug(BugDTO bugDTO);
    Bug updateBug(Long id, BugDTO bugDTO);
    void deleteBug(Long id);

}
