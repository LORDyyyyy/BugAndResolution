package com.BugAndResolution.BugAndResolution.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BugAndResolution.BugAndResolution.model.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBugId(Long bugId);
}
