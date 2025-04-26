package com.BugAndResolution.BugAndResolution.controller.commentController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BugAndResolution.BugAndResolution.dto.comment.CommentRequestDTO;
import com.BugAndResolution.BugAndResolution.dto.comment.CommentResponseDTO;
import com.BugAndResolution.BugAndResolution.service.commentServices.CommentServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentServices commentServices;

    @PostMapping("/create")
    public ResponseEntity<CommentResponseDTO> createComment(@Valid @RequestBody CommentRequestDTO commentRequestDTO) {
        CommentResponseDTO createdComment =  commentServices.createComment(commentRequestDTO);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @GetMapping("{bugId}")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByBugId(@PathVariable Long bugId) {
        List<CommentResponseDTO> comments = commentServices.getCommentsByBugId(bugId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDTO> updateComment(@PathVariable Long commentId, @Valid @RequestBody CommentRequestDTO commentRequestDTO) {
        CommentResponseDTO updatedComment = commentServices.updateComment(commentId, commentRequestDTO);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }
    
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentServices.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
