package com.example.final_project.Service;

import com.example.final_project.Model.Complaint;
import com.example.final_project.Repository.ComplaintRepository;
import com.example.final_project.Repository.CenterRepository;
import com.example.final_project.Repository.ParentReposotiry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final ParentReposotiry parentRepository;
    private final CenterRepository centerRepository;

    public void addComplaint(@Valid Complaint complaint) {
        // Check if the parent exists
        if (!parentRepository.existsById(complaint.getParent().getId())) {
            throw new RuntimeException("Parent not found");
        }

        // Check if the center exists
        if (!centerRepository.existsById(complaint.getCenter().getId())) {
            throw new RuntimeException("Center not found");
        }

        complaintRepository.save(complaint);
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Complaint getComplaintById(Integer id) {
        return complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
    }

//    public Complaint updateComplaint(Integer id, @Valid Complaint updatedComplaint) {
//        Complaint existingComplaint = getComplaintById(id);
//
//        // Update fields
//        existingComplaint.setContent(updatedComplaint.getContent());
//        existingComplaint.setCenter(updatedComplaint.getCenter());
//        existingComplaint.setParent(updatedComplaint.getParent());
//
//        return complaintRepository.save(existingComplaint);
//    }

    public void deleteComplaint(Integer id) {
        Complaint existingComplaint = getComplaintById(id);
        complaintRepository.delete(existingComplaint);
    }
}