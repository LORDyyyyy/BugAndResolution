package com.BugAndResolution.BugAndResolution.controller.bugController;

import com.BugAndResolution.BugAndResolution.dto.bugs.BugRequestDTO;
import com.BugAndResolution.BugAndResolution.dto.bugs.BugResponseDTO;
import com.BugAndResolution.BugAndResolution.dto.bugs.BugUpdateDTO;
import com.BugAndResolution.BugAndResolution.dto.user.DeveloperAssignmentDTO;
import com.BugAndResolution.BugAndResolution.service.bugServices.BugService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bugs")
public class BugController {

    @Autowired
    private BugService bugService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('TESTER')")
    public ResponseEntity<BugResponseDTO> createBug(@RequestBody @Valid BugRequestDTO dto) {
        BugResponseDTO responseDTO = bugService.submitBug(dto);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<BugResponseDTO>> getAllBugs() {
        return ResponseEntity.ok(bugService.getAllBugs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BugResponseDTO> getBugById(@PathVariable Long id) {
        return ResponseEntity.ok(bugService.getBugById(id));
    }

    @GetMapping("developer/{developerId}")
    public ResponseEntity<List<BugResponseDTO>> getBugsByDeveloperId(@PathVariable Long developerId) {
        return ResponseEntity.ok(bugService.getBugsByDeveloperId(developerId));
    }

    @PutMapping("/{bugId}")
    @PreAuthorize("hasRole('DEVELOPER') or hasRole('TESTER')")
    public ResponseEntity<BugResponseDTO> updateBug(@PathVariable Long bugId, @RequestBody BugUpdateDTO dto) {

        dto.setBugId(bugId);
        return ResponseEntity.ok(bugService.updateBug(dto));
    }
    
    @GetMapping("/filter-by-developer/{developerId}")
    public ResponseEntity<List<BugResponseDTO>> filterByDeveloperId(@PathVariable Long developerId) {
        return ResponseEntity.ok(bugService.getBugsByDeveloperId(developerId));
    }

    @GetMapping("filter-by-status/{status}")
    public ResponseEntity<List<BugResponseDTO>> filterByStatus(@PathVariable String status) {
        return ResponseEntity.ok(bugService.getBugsByStatus(status));
    }

    @GetMapping("filter-by-severity/{severity}")
    public ResponseEntity<List<BugResponseDTO>> filterBySeverity(@PathVariable String severity) {
        return ResponseEntity.ok(bugService.getBugsBySeverity(severity));
    }

    @DeleteMapping("{bugId}")
    @PreAuthorize("hasRole('TESTER')")
    public ResponseEntity<BugResponseDTO>deleteBug(@PathVariable Long bugId){
        bugService.deleteBug(bugId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/assign/{bugId}")
    public ResponseEntity<BugResponseDTO> assignBugToDeveloper(
            @PathVariable Long bugId,
            @Valid @RequestBody DeveloperAssignmentDTO assignmentDTO) {
        
        return ResponseEntity.ok(bugService.assignBugToDeveloper(bugId, assignmentDTO.getDeveloperId()));
    }

}
