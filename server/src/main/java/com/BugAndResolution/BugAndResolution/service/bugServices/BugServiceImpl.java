package com.BugAndResolution.BugAndResolution.service.bugServices;

import com.BugAndResolution.BugAndResolution.dto.BugDTO;
import com.BugAndResolution.BugAndResolution.model.entities.Bug;
import com.BugAndResolution.BugAndResolution.repository.BugRepository;
import com.BugAndResolution.BugAndResolution.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BugServiceImpl implements BugService{

    private final BugRepository bugRepository;

    @Override
    public List<Bug> getAllBugs() {
        return bugRepository.findAll();
    }

    @Override
    public Optional<Bug> getBugById(Long id) {
        return bugRepository.findById(id);
    }

    @Override
    public Bug postBug(BugDTO bugDTO) {
        Bug tempBug=new Bug();
        tempBug.setTitle(bugDTO.getTitle());
        tempBug.setId(bugDTO.getId());
        tempBug.setCreatedAt(bugDTO.getCreatedAt());
        tempBug.setDescription(bugDTO.getDescription());
        tempBug.setSeverity(bugDTO.getSeverity());
        tempBug.setStatus(bugDTO.getStatus());
        tempBug.setUpdatedAt(bugDTO.getUpdatedAt());
        return bugRepository.save(tempBug);
    }

    @Override
    public Bug updateBug(Long id, BugDTO bugDTO) {
        return null;
    }

    @Override
    public void deleteBug(Long id) {
        Optional<Bug> tempBug= bugRepository.findById(id);
        if(tempBug.isPresent()){
            bugRepository.delete(tempBug.get());
        }
    }
}
