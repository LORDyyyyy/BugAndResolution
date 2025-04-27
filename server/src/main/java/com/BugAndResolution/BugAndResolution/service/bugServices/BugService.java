package com.BugAndResolution.BugAndResolution.service.bugServices;

import com.BugAndResolution.BugAndResolution.dto.bugs.BugRequestDTO;
import com.BugAndResolution.BugAndResolution.dto.bugs.BugResponseDTO;
import com.BugAndResolution.BugAndResolution.dto.bugs.BugUpdateDTO;
import com.BugAndResolution.BugAndResolution.exception.ResourceNotFoundException;
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
        User submittedBy = null;
        if (dto.getSubmittedById() != null) {
            submittedBy = userRepository.findById(dto.getSubmittedById())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
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
        Bug bug=bugRepository.findById(bugId).orElseThrow(()->new ResourceNotFoundException("Bug not found"));
        bugRepository.delete(bug);
    }




    public BugResponseDTO updateBug(BugUpdateDTO dto) {
        Bug bug = bugRepository.findById(dto.getBugId())
                .orElseThrow(() -> new ResourceNotFoundException("Bug not found"));

            bug.setStatus(dto.getStatus());
            User dev = userRepository.findById(dto.getDeveloperId())
                    .orElseThrow(() -> new ResourceNotFoundException("Developer not found"));
            bug.setDeveloper(dev);
            User submitted=userRepository.findById(dto.getSubmittedById())
                    .orElseThrow(()->new ResourceNotFoundException("User not found"));
            bug.setSubmittedBy(submitted);
            bug.setTitle(dto.getTitle());
            bug.setDescription(dto.getDescription());
            bug.setSeverity(dto.getSeverity());
            bug.setStatus(dto.getStatus());
        Bug updated = bugRepository.save(bug);
        return mapToBugResponseDTO(updated);
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
