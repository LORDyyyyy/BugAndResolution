package com.BugAndResolution.BugAndResolution.service.bugServices;

import com.BugAndResolution.BugAndResolution.dto.bugs.BugRequestDTO;
import com.BugAndResolution.BugAndResolution.dto.bugs.BugResponseDTO;
import com.BugAndResolution.BugAndResolution.dto.bugs.BugUpdateDTO;
import com.BugAndResolution.BugAndResolution.exception.AccessDeniedException;
import com.BugAndResolution.BugAndResolution.exception.ResourceNotFoundException;
import com.BugAndResolution.BugAndResolution.model.entities.Bug;
import com.BugAndResolution.BugAndResolution.model.entities.User;
import com.BugAndResolution.BugAndResolution.model.enums.Status;
import com.BugAndResolution.BugAndResolution.repository.BugRepository;
import com.BugAndResolution.BugAndResolution.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BugService {

    @Autowired
    private BugRepository bugRepository;
    @Autowired
    private UserRepository userRepository;

    public BugResponseDTO submitBug(BugRequestDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        
        User submittedBy = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found"));
        
        if (!"TESTER".equals(submittedBy.getRole().name())) {
            throw new AccessDeniedException("Only testers can submit bugs");
        }
        
        User developer = null;
        if (dto.getDeveloperId() != null) {
            developer = userRepository.findById(dto.getDeveloperId())
                    .orElseThrow(() -> new ResourceNotFoundException("Developer not found"));
        }

        Bug bug = new Bug(dto.getTitle(), dto.getDescription(), dto.getSeverity(), Status.OPEN);
        bug.setSubmittedBy(submittedBy);
        bug.setDeveloper(developer);
        Bug saved = bugRepository.save(bug);
        return mapToBugResponseDTO(saved);
    }





    public List<BugResponseDTO> getAllBugs() {
        List<Bug> bugs = bugRepository.findAll();
        List<BugResponseDTO> dtos = new ArrayList<>();
        for (Bug b : bugs) {
            dtos.add(mapToBugResponseDTO(b));
        }
        return dtos;
    }





    public BugResponseDTO getBugById(Long bugId) {
        Bug bug = bugRepository.findById(bugId)
                .orElseThrow(() -> new ResourceNotFoundException("Bug not found"));

        return mapToBugResponseDTO(bug);
    }



    public void deleteBug(Long bugId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found"));
        
        if (!"TESTER".equals(user.getRole().name())) {
            throw new AccessDeniedException("Only testers can delete bugs");
        }
        if (bugRepository.findById(bugId).isEmpty()) {
            throw new ResourceNotFoundException("Bug not found");
        }
        Bug bug=bugRepository.findById(bugId).orElseThrow(()->new ResourceNotFoundException("Bug not found"));
        bugRepository.delete(bug);
    }




    public BugResponseDTO updateBug(BugUpdateDTO dto) {
        Bug bug = bugRepository.findById(dto.getBugId())
        .orElseThrow(() -> new ResourceNotFoundException("Bug not found"));

        if (dto.getTitle() != null) {
            bug.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            bug.setDescription(dto.getDescription());
        }
        if (dto.getSeverity() != null) {
            bug.setSeverity(dto.getSeverity());
        }
        if (dto.getStatus() != null) {
            bug.setStatus(dto.getStatus());
        }
        if (dto.getDeveloperId() != null) {
            User developer = userRepository.findById(dto.getDeveloperId())
                    .orElseThrow(() -> new ResourceNotFoundException("Developer not found"));
            bug.setDeveloper(developer);
        }

        return mapToBugResponseDTO(bugRepository.save(bug));
    }

    public List<BugResponseDTO> getBugsByDeveloperId(Long developerId) {
        List<Bug> bugs = bugRepository.findAllByDeveloperId(developerId);
        List<BugResponseDTO> dtos = new ArrayList<>();
        for (Bug b : bugs) {
            dtos.add(mapToBugResponseDTO(b));
        }
        return dtos;
    }


    public List<BugResponseDTO> getBugsByStatus(String statusStr) {
        Status status = Status.valueOf(statusStr.toUpperCase());
        List<Bug> bugs = bugRepository.findAllByStatus(status);
        List<BugResponseDTO> dtos = new ArrayList<>();
        for (Bug b : bugs) {
            dtos.add(mapToBugResponseDTO(b));
        }
        return dtos;
    }

    public List<BugResponseDTO> getBugsBySeverity(String severity) {
        List<Bug> bugs = bugRepository.findAllBySeverity(severity);
        List<BugResponseDTO> dtos = new ArrayList<>();
        for (Bug b : bugs) {
            dtos.add(mapToBugResponseDTO(b));
        }
        return dtos;
    }

    public BugResponseDTO assignBugToDeveloper(Long bugId, Long developerId) {        
        Bug bug = bugRepository.findById(bugId)
                .orElseThrow(() -> new ResourceNotFoundException("Bug not found with id: " + bugId));
        
        User developer = userRepository.findById(developerId)
                .orElseThrow(() -> new ResourceNotFoundException("Developer not found with id: " + developerId));
        
        if (!"DEVELOPER".equals(developer.getRole().name())) {
            throw new AccessDeniedException("The selected user is not a developer");
        }

        if (bug.getDeveloper().getId().equals(developerId)) {
            throw new AccessDeniedException("The bug is already assigned to this developer");
        }
        
        bug.setDeveloper(developer);
        bug.setStatus(Status.IN_PROG);
        bug = bugRepository.save(bug);
        
        return mapToBugResponseDTO(bug);
    }

    private BugResponseDTO mapToBugResponseDTO(Bug bug) {
        BugResponseDTO dto = new BugResponseDTO();
        dto.setId(bug.getId());
        dto.setTitle(bug.getTitle());
        dto.setDescription(bug.getDescription());
        dto.setSeverity(bug.getSeverity());
        dto.setStatus(bug.getStatus());
        dto.setCreatedAt(bug.getCreatedAt());
        dto.setUpdatedAt(bug.getUpdatedAt());
        if (bug.getDeveloper() != null) {
            dto.setDeveloperId(bug.getDeveloper().getId());
        }
        if (bug.getSubmittedBy() != null) {
            dto.setSubmittedById(bug.getSubmittedBy().getId());
        }
        return dto;
    }


}
