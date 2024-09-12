package com.example.final_project.Controller;

import com.example.final_project.API.ApiResponse;
import com.example.final_project.DTO.ParentDTO;
import com.example.final_project.Model.Parent;
import com.example.final_project.Model.User;
import com.example.final_project.Service.ParentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parent")
@RequiredArgsConstructor
public class ParentController {
    private final ParentService parentService;

    // Get all parents
    @GetMapping("/get-all")
    public ResponseEntity getAllParents() {
        List<Parent> parents = parentService.getAllParents();
        return ResponseEntity.ok(parents);
    }

    // Get parent by ID
//    @GetMapping("/{id}")
//    public ResponseEntity getParentById(@AuthenticationPrincipal User user, @PathVariable Integer id) {
//        Parent parent = parentService.getParentById(id)
//                .orElseThrow(() -> new RuntimeException("Parent not found with ID: " + id));
//        return ResponseEntity.ok(parent);
//    }

    // Add a new parent
    @PostMapping("/register")
    public ResponseEntity addParent(@Valid @RequestBody ParentDTO parentDTO) {
       parentService.addParent(parentDTO);
        return ResponseEntity.status(201).body("parent added"); // return ResponseEntity.ok(new ApiResponse("Parent added successfully"));
    }

    // Update an existing parent
    @PutMapping("/update")
    public ResponseEntity updateParent(@AuthenticationPrincipal User user, @RequestBody ParentDTO parentDTO) {
        parentService.updateParent(user.getId(), parentDTO);
        return ResponseEntity.status(200).body("parent updated " );
    }

    // Delete a parent by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteParent(@PathVariable Integer id) {
        parentService.delete(id);
        return ResponseEntity.status(200).body("user deleted successfully");
    }
}