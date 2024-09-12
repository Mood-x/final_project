package com.example.final_project.Controller;

import com.example.final_project.Model.Child;
import com.example.final_project.Model.User;
import com.example.final_project.Service.ChildService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/child")
@RequiredArgsConstructor
public class ChildController {
    private final ChildService childService;

    // Get all children
    @GetMapping("/get-all")
    public ResponseEntity<List<Child>> getAllChildren() {

        return ResponseEntity.ok(childService.getAllChildren());
    }

    // Add a new child
    @PostMapping("/register")
    public ResponseEntity addChild(@AuthenticationPrincipal User user, @Valid @RequestBody Child child) {
        childService.addChild(user.getId(), child);
        return ResponseEntity.status(200).body("child added successfully");
    }

    // Update an existing child
    @PutMapping("/update/{id}")
    public ResponseEntity updateChild(@AuthenticationPrincipal User user, @PathVariable Integer id, @Valid @RequestBody Child updatedChild) {
        childService.updateChild(user.getId(), id, updatedChild);
        return ResponseEntity.status(200).body("child updated successfully");
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteChild(@AuthenticationPrincipal User user,
                                              @PathVariable Integer id) {
        // Call the service to delete the child
        childService.deleteParent(user.getId(), id);
        return ResponseEntity.ok("Child deleted successfully.");
    }
}