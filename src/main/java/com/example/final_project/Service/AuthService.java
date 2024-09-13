package com.example.final_project.Service;


import com.example.final_project.Model.User;
import com.example.final_project.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;

    public List<User> getAllUsers() {
        return authRepository.findAll();
    }
// حذف
    public void register(User user){
        user.setRole("ADMIN");
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        authRepository.save(user);
    }

//    @Scheduled
//    public void deleteExpiredAccount() {
//        List<User> usersToDelete = authRepository.findAllByPendingDeletionTrue()
//                .orElse(null);
//        LocalDateTime now = LocalDateTime.now();
//
//        usersToDelete.forEach(user -> {
//            if(user.getDeletionRequestDate().plusDays(10).isBefore(now)){
//                authRepository.delete(user);
//            }
//        });
//    }

//    public void cancleDeletionRequest(Integer userId){
//        User user = authRepository.findUserById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        if(!user.isPendingDeletion()){
//            throw new RuntimeException("No deletion request found");
//        }
//
//        user.setPendingDeletion(false);
//        user.setDeletionRequestDate(null);
//        authRepository.save(user);
//    }
}
