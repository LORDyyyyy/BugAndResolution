package com.BugAndResolution.BugAndResolution.service.bugServices;

import com.BugAndResolution.BugAndResolution.dto.bugs.BugRequestDTO;
import com.BugAndResolution.BugAndResolution.dto.bugs.BugResponseDTO;
import com.BugAndResolution.BugAndResolution.dto.bugs.BugUpdateDTO;
import com.BugAndResolution.BugAndResolution.model.entities.Bug;
import com.BugAndResolution.BugAndResolution.model.entities.User;
import com.BugAndResolution.BugAndResolution.model.enums.Status;
import com.BugAndResolution.BugAndResolution.repository.BugRepository;
import com.BugAndResolution.BugAndResolution.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        User submittedBy = userRepository.findById(dto.getSubmittedById())
                .orElseThrow(() -> new RuntimeException("User not found"));
        User developer = null;
        if (dto.getDeveloperId() != null) {
            developer = userRepository.findById(dto.getDeveloperId())
                    .orElseThrow(() -> new RuntimeException("Developer not found"));
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
                .orElseThrow(() -> new RuntimeException("Bug not found"));

        return mapToBugResponseDTO(bug);
    }





    public BugResponseDTO updateBug(BugUpdateDTO dto) {
        Bug bug = bugRepository.findById(dto.getBugId())
                .orElseThrow(() -> new RuntimeException("Bug not found"));

        if (dto.getStatus() != null) {
            bug.setStatus(dto.getStatus());
        }
        if (dto.getDeveloperId() != null) {
            User dev = userRepository.findById(dto.getDeveloperId())
                    .orElseThrow(() -> new RuntimeException("Developer not found"));
            bug.setDeveloper(dev);
        }
        if(dto.getSubmittedById()!=null){
            User submitted=userRepository.findById(dto.getSubmittedById())
                    .orElseThrow(()->new RuntimeException("User not found"));

            bug.setSubmittedBy(submitted);
        }

        if (dto.getTitle() != null) {
            bug.setTitle(dto.getTitle());
        }

        if (dto.getDescription() != null) {
            bug.setDescription(dto.getDescription());
        }

        if(dto.getSeverity()!=null){
            bug.setSeverity(dto.getSeverity());
        }
        if (dto.getStatus() != null) {
            bug.setStatus(dto.getStatus());
        }

        Bug updated = bugRepository.save(bug);
        return mapToBugResponseDTO(updated);
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
