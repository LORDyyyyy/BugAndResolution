package com.BugAndResolution.BugAndResolution.repository;

import com.BugAndResolution.BugAndResolution.model.entities.Bug;
import com.BugAndResolution.BugAndResolution.model.enums.Status;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BugRepository extends JpaRepository<Bug,Long> {
    List<Bug> findAllByDeveloperId(Long DeveloperId);
    List<Bug> findAllByStatus(Status status);
}
