package com.example.final_project.Controller;

import com.example.final_project.API.ApiResponse;
import com.example.final_project.Model.User;
import com.example.final_project.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/get-all-users")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(authService.getAllUsers());
    }

//    @PostMapping("/register")
//    public ResponseEntity<ApiResponse> register(@Valid @RequestBody User user){
//        authService.register(user);
//        return ResponseEntity.ok(new ApiResponse("User added successfully"));
//    }
}
