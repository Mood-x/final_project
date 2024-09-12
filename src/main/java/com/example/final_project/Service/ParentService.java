package com.example.final_project.Service;

import com.example.final_project.API.ApiException;
import com.example.final_project.DTO.ParentDTO;
import com.example.final_project.Model.Parent;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.AuthRepository;
import com.example.final_project.Repository.ParentReposotiry;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParentService {
    private final ParentReposotiry parentRepository;
    private final AuthRepository authRepository;

    // Get all parents AdminParent
    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }

    // Get parent by ID
    public Parent getParentById(Integer id) {
        return parentRepository.findParentById(id).orElseThrow(() -> new ApiException("Parent Not Found"));
    }

    public void addParent(ParentDTO parentDTO) {
        // Create and populate User entity
        User user = new User();
        user.setUsername(parentDTO.getUsername());
        user.setPassword(parentDTO.getPassword());
        user.setEmail(parentDTO.getEmail());
        user.setName(parentDTO.getName());
        user.setRole("PARENT");

        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        authRepository.save(user);
        Parent parent = new Parent();
        parent.setUser(user);

         parentRepository.save(parent); // Return the saved Parent
    }

    // Update an existing parent
    public void updateParent(Integer id, ParentDTO parentDTO) {
        Parent existingParent = parentRepository.findParentById(id)
                .orElseThrow(() -> new ApiException("Parent not found with ID: " + id));

        User user = existingParent.getUser();
        user.setUsername(parentDTO.getUsername());
        user.setEmail(parentDTO.getEmail());
        user.setName(parentDTO.getName());

        if (parentDTO.getPassword() != null && !parentDTO.getPassword().isEmpty()) {
            String hash = new BCryptPasswordEncoder().encode(parentDTO.getPassword());
            user.setPassword(hash);
        }

        authRepository.save(user);
    }



    public void delete(Integer authId){
        User user  = authRepository.findUserById(authId)
                .orElseThrow(() -> new ApiException("User not found"));
        authRepository.delete(user);
    }
}