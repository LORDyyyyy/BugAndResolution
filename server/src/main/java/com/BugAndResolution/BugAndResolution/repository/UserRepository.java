package com.BugAndResolution.BugAndResolution.repository;

import com.BugAndResolution.BugAndResolution.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
