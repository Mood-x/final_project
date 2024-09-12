package com.example.final_project.Service;

import com.example.final_project.API.ApiException;
import com.example.final_project.Model.Child;
import com.example.final_project.Model.Parent;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.AuthRepository;
import com.example.final_project.Repository.ChildRepository;
import com.example.final_project.Repository.ParentReposotiry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChildService {
    private final ChildRepository childRepository;
    private final AuthRepository authRepository;
    private final ParentReposotiry parentReposotiry;

    public List<Child> getAllChildren() {
        return childRepository.findAll();
    }

//    // Get child by ID
//    public Optional<Child> getChildById(Integer id) {
//        return childRepository.findById(id);
//    }

    // Add a new child
    public void addChild(Integer userId, Child child) {
        User user = authRepository.findUserById(userId).orElseThrow(() -> new ApiException("User not found"));


        // Get the parent associated with the user
        Parent parent = user.getParent(); // Assuming User has a getParent() method
        if (parent == null) {
            throw new RuntimeException("Parent not found for the user");
        }

        // Set the parent for the child
        child.setParent(parent); // Associate the child with the parent

        // Save the child
        childRepository.save(child);
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
}