package com.example.final_project.Controller;

import com.example.final_project.DTO.CenterDTO;
import com.example.final_project.Model.User;
import com.example.final_project.Service.CenterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/center")
public class CenterController {
    private final CenterService centerService;

    @GetMapping("/get-all-centers")
    public ResponseEntity getAllCenters(){
        return ResponseEntity.status(200).body(centerService.getAllCenters());
    }

    @PostMapping("/center-register")
    public ResponseEntity centerRegister(@RequestBody @Valid CenterDTO centerDTO){
        centerService.centerRegister(centerDTO);
        return ResponseEntity.status(200).body("Center registered successfully");
    }

    @PutMapping("/update-center")
    public ResponseEntity updateCenter(@AuthenticationPrincipal User user, @RequestBody @Valid CenterDTO centerDTO){
        centerService.updateCenter(user.getId(),centerDTO);
        return ResponseEntity.status(200).body("Center updated successfully");
    }

    @DeleteMapping("/delete-center/{centerid}")
    public ResponseEntity deleteCenter(@PathVariable int centerid, @AuthenticationPrincipal User user){
        centerService.deleteCenter(user.getId(),centerid);
        return ResponseEntity.status(200).body("Center deleted successfully");
    }
}
