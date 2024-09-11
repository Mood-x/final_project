package com.example.final_project.Service;


import com.example.final_project.Model.User;
import com.example.final_project.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;

    public List<User> getAllUsers() {
        return authRepository.findAll();
    }

    public void register(User user){
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        authRepository.save(user);
    }
}
