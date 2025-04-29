package com.BugAndResolution.BugAndResolution.service.commentServices;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BugAndResolution.BugAndResolution.dto.comment.CommentRequestDTO;
import com.BugAndResolution.BugAndResolution.dto.comment.CommentResponseDTO;
import com.BugAndResolution.BugAndResolution.exception.ResourceNotFoundException;
import com.BugAndResolution.BugAndResolution.model.entities.Bug;
import com.BugAndResolution.BugAndResolution.model.entities.Comment;
import com.BugAndResolution.BugAndResolution.model.entities.User;
import com.BugAndResolution.BugAndResolution.repository.BugRepository;
import com.BugAndResolution.BugAndResolution.repository.CommentRepository;
import com.BugAndResolution.BugAndResolution.repository.UserRepository;

@Service
public class CommentServices {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BugRepository bugRepository;
    @Autowired
    private UserRepository userRepository;

    public CommentResponseDTO createComment(CommentRequestDTO commentRequestDTO) {
        Comment comment = new Comment();
        Bug bug = bugRepository.findById(commentRequestDTO.getBugId())
                .orElseThrow(() -> new ResourceNotFoundException("Bug not found with id: " + commentRequestDTO.getBugId()));
        User user = userRepository.findById(commentRequestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + commentRequestDTO.getUserId()));

        comment.setComment(commentRequestDTO.getContent());
        comment.setUser(user);
        comment.setBug(bug);
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);

        return convertToDTO(savedComment);
    }

    public List<CommentResponseDTO> getCommentsByBugId(Long bugId) {
        List<Comment> comments = commentRepository.findByBugId(bugId);
        return comments.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public CommentResponseDTO getCommentById(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            return convertToDTO(comment.get());
        } else {
            throw new ResourceNotFoundException("Comment not found");
        }
    }

    public void deleteComment(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            commentRepository.delete(comment.get());
        } else {
            throw new ResourceNotFoundException("Comment not found");
        }
    }

    public CommentResponseDTO updateComment(Long commentId, CommentRequestDTO commentRequestDTO) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setComment(commentRequestDTO.getContent());
            comment.setCreatedAt(LocalDateTime.now());
            Comment updatedComment = commentRepository.save(comment);
            return convertToDTO(updatedComment);
        } else {
            throw new RuntimeException("Comment not found");
        }
    }




    private CommentResponseDTO convertToDTO(Comment comment) {
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getComment());
        dto.setUserId(comment.getUser().getId().longValue());
        dto.setBugId(comment.getBug().getId());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }
}
