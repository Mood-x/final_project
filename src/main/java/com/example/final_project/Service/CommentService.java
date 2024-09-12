package com.example.final_project.Service;

import com.example.final_project.Model.Comment;
import com.example.final_project.Repository.CommentRepository;

import com.example.final_project.Repository.CenterRepository;
import com.example.final_project.Repository.ParentReposotiry;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ParentReposotiry parentRepository;
    private final CenterRepository centerRepository;

    public Comment addComment(@Valid Comment comment) {
        // Check if the parent exists
        if (!parentRepository.existsById(comment.getParent().getId())) {
            throw new RuntimeException("Parent not found");
        }

        // Check if the center exists
        if (!centerRepository.existsById(comment.getCenter().getId())) {
            throw new RuntimeException("Center not found");
        }

        return commentRepository.save(comment);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment getCommentById(Integer id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    public void updateComment(Integer id, @Valid Comment updatedComment) {
        Comment existingComment = getCommentById(id);

        // Update fields
        existingComment.setContent(updatedComment.getContent());
        existingComment.setCenter(updatedComment.getCenter());
        existingComment.setParent(updatedComment.getParent());
        existingComment.setCreatedAt(updatedComment.getCreatedAt());

        commentRepository.save(existingComment);
    }


    public void deleteComment(Integer id) {
        Comment existingComment = getCommentById(id);
        commentRepository.delete(existingComment);
    }
}