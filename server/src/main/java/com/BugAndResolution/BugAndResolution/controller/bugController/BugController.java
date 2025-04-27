package com.BugAndResolution.BugAndResolution.controller.bugController;

import com.BugAndResolution.BugAndResolution.dto.bugs.BugRequestDTO;
import com.BugAndResolution.BugAndResolution.dto.bugs.BugResponseDTO;
import com.BugAndResolution.BugAndResolution.dto.bugs.BugUpdateDTO;
import com.BugAndResolution.BugAndResolution.service.bugServices.BugService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bugs")
public class BugController {

    @Autowired
    private BugService bugService;

    @PostMapping
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

    @PutMapping("/{bugId}")
    public ResponseEntity<BugResponseDTO> updateBug(@PathVariable Long bugId,@RequestBody BugUpdateDTO dto) {

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

    @DeleteMapping("{bugId}")
    public ResponseEntity<BugResponseDTO>deleteBug(@PathVariable Long bugId){
        bugService.deleteBug(bugId);
        return ResponseEntity.noContent().build();
    }

}
