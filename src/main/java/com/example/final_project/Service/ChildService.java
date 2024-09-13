package com.example.final_project.Service;

import com.example.final_project.API.ApiException;
import com.example.final_project.Model.Child;
import com.example.final_project.Model.Parent;
import com.example.final_project.Model.Program;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.AuthRepository;
import com.example.final_project.Repository.ChildRepository;
import com.example.final_project.Repository.ParentReposotiry;
import com.example.final_project.Repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChildService {
    private final ChildRepository childRepository;
    private final AuthRepository authRepository;
    private final ParentReposotiry parentReposotiry;
    private final ProgramRepository programRepository;

    public List<Child> getAllChildren() {
        return childRepository.findAll();
    }

//    // Get child by ID
//    public Optional<Child> getChildById(Integer id) {
//        return childRepository.findById(id);
//    }

    // Add a new child
    public void addChild(Integer userId, Child child) {
        User user = authRepository.findUserById(userId)
                .orElseThrow(() -> new ApiException("User not found"));

        Parent parent = parentReposotiry.findParentById(user.getId())
                .orElseThrow(() -> new ApiException("Parent not found"));

        parent.getChildren().add(child);
        child.setParent(parent);
        childRepository.save(child);
        // Get the parent associated with the user
        //Parent parent = user.getParent(); // Assuming User has a getParent() method
        //if (parent == null) {
           // throw new RuntimeException("Parent not found for the user");
        //}

        // Set the parent for the child
        //child.setParent(parent); // Associate the child with the parent

        // Save the child
        //childRepository.save(child);
    }
    // Update an existing child
    public void updateChild(Integer userId, Integer childId, Child updatedChild) {
        // Check if the user exists
        User user = authRepository.findUserById(userId).orElseThrow(() -> new ApiException("User not found"));
        // Check if the child exists
        Child existingChild = childRepository.findById(childId)
                .orElseThrow(() -> new RuntimeException("Child not found with ID: " + childId));

        // Check if the parent associated with the child exists
        Parent parent = existingChild.getParent();
        if (parent == null) {
            throw new RuntimeException("Parent not found for the child");
        }

        // Update the child's fields
        existingChild.setName(updatedChild.getName());
        existingChild.setPicUrl(updatedChild.getPicUrl());
        existingChild.setAge(updatedChild.getAge());
        existingChild.setJoinDate(updatedChild.getJoinDate());
        existingChild.setGender(updatedChild.getGender());

        // Save and return the updated child
        childRepository.save(existingChild);
    }

    //    // Delete a child by ID
//    public void deleteChild(Integer userId, Integer id) {
//        if (!childRepository.existsById(id)) {
//            throw new RuntimeException("Child not found with ID: " + id);
//        }
//        childRepository.deleteById(id);
//    }
    public void deleteParent(Integer userId, Integer id) {
        User user = authRepository.findUserById(userId).orElseThrow(() -> new ApiException("User not found"));

        // Check if the parent exists
        Parent parent = parentReposotiry.findById(id)
                .orElseThrow(() -> new RuntimeException("Parent not found with ID: " + id));

        parentReposotiry.delete(parent);
    }
    // Method to add a child to a program
    public void addChildToProgram(Integer userId, Integer childId, Integer programId) {
        // Check if user exists
        User user = authRepository.findUserById(userId)
                .orElseThrow(() -> new ApiException("User not found"));

        // Get the parent associated with the user
        Parent parent = user.getParent();
        if (parent == null) {
            throw new RuntimeException("Parent not found for the user");
        }

        // Find the child by ID
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new RuntimeException("Child not found with ID: " + childId));

        // Check if the child is already registered under this parent
        if (!child.getParent().equals(parent)) {
            throw new ApiException("You cannot add this child to the program, they belong to a different parent.");
        }

        // Find the program by ID
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new RuntimeException("Program not found with ID: " + programId));

        // Check if the child is already subscribed to this program
        if (child.getPrograms().contains(program)) {
            throw new ApiException("Child has already applied to this program.");
        }

        // Add the program to the child's set of programs
        child.getPrograms().add(program);

        // Save the child with the updated programs
        childRepository.save(child);
    }
    // Method to get all programs for a parent's children
    public List<Program> getAllProgramsForParent(Integer userId) {
        // Check if user exists
        User user = authRepository.findUserById(userId)
                .orElseThrow(() -> new ApiException("User not found"));

        // Get the parent associated with the user
        Parent parent = user.getParent();
        if (parent == null) {
            throw new ApiException("Parent not found for the user");
        }

        // Get all children of the parent
        List<Child> children = childRepository.findAllByParent(parent);

        // Collect all programs the children are subscribed to
        Set<Program> programs = new HashSet<>();
        for (Child child : children) {
            programs.addAll(child.getPrograms());
        }

        return new ArrayList<>(programs);
    }
    public String cancelProgram(Integer childId, Integer programId) {
        // Find the child
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new ApiException("Child not found"));

        // Find the program
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new ApiException("Program not found"));

        // Check if the child is enrolled in the program
        if (!child.getPrograms().contains(program)) {
            throw new ApiException("Child is not enrolled in this program");
        }

        // Get current date and program's start date
        Date currentDate = new Date();
        Date programStartDate = program.getStartDate();

        double refundAmount = 0.0;
        double programPrice = program.getPrice();

        // Check if the program has started or not
        if (currentDate.before(programStartDate)) {
            // Before start date: 100% refund
            refundAmount = programPrice;
        } else {
            // After start date: 30% refund
            refundAmount = programPrice * 0.30;
        }

        // Remove the program from the child's enrolled programs
        child.getPrograms().remove(program);
        childRepository.save(child);

        // Return a message including refund amount
        return "Program cancelled. Refund: SR" + refundAmount;
    }
}