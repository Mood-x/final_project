package com.example.final_project.Controller;

import com.example.final_project.Model.Comment;
import com.example.final_project.Model.User;
import com.example.final_project.Service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity addComment(@Valid @RequestBody Comment comment, @AuthenticationPrincipal User user) {
        comment.setParent(user.getParent());
        Comment createdComment = commentService.addComment(comment);
        return ResponseEntity.status(201).body(createdComment);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Integer id) {
        Comment comment = commentService.getCommentById(id);
        return ResponseEntity.ok(comment);
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<Comment> updateComment(@PathVariable Integer id, @Valid @RequestBody Comment updatedComment, @AuthenticationPrincipal User user) {
//        updatedComment.setParent(user.getParent()); // Set the parent if necessary
//        Comment comment = commentService.updateComment(id, updatedComment);
//        return ResponseEntity.ok(comment);
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return ResponseEntity.status(200).body("Comment deleted");
    }
}