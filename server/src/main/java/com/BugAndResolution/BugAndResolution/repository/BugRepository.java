package com.BugAndResolution.BugAndResolution.repository;

import com.BugAndResolution.BugAndResolution.model.entities.Bug;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BugRepository extends JpaRepository<Bug,Long> {
}
