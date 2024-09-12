package com.example.final_project.Controller;

import com.example.final_project.Model.Complaint;
import com.example.final_project.Model.User;
import com.example.final_project.Service.ComplaintService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/complaint")
public class ComplaintController {

    private ComplaintService complaintService;

    @PostMapping("/add")
    public ResponseEntity addComplaint(@Valid @RequestBody Complaint complaint, @AuthenticationPrincipal User user) {
        complaint.setParent(user.getParent());
        complaintService.addComplaint(complaint);
        return ResponseEntity.status(201).body("complaint added");
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        List<Complaint> complaints = complaintService.getAllComplaints();
        return ResponseEntity.ok(complaints);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Complaint> getComplaintById(@PathVariable Integer id) {
        Complaint complaint = complaintService.getComplaintById(id);
        return ResponseEntity.ok(complaint);
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<Complaint> updateComplaint(@PathVariable Integer id, @Valid @RequestBody Complaint updatedComplaint, @AuthenticationPrincipal User user) {
//        updatedComplaint.setParent(user.getParent()); // Set the parent if necessary
//        Complaint complaint = complaintService.updateComplaint(id, updatedComplaint);
//        return ResponseEntity.ok(complaint);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteComplaint(@PathVariable Integer id) {
        complaintService.deleteComplaint(id);
        return ResponseEntity.status(200).body("complaint deleted successfully");
    }
}