package com.example.final_project.Controller;

import com.example.final_project.DTO.CenterDTO;
import com.example.final_project.Model.Program;
import com.example.final_project.Model.User;
import com.example.final_project.Service.CenterService;
import com.example.final_project.Service.ProgramService;
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
    private final ProgramService programService;

    @GetMapping("/get-all-centers")
    public ResponseEntity getAllCenters(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(centerService.getAllCenters());
    }

    @PostMapping("/center-register")
    public ResponseEntity centerRegister(@Valid @RequestBody CenterDTO centerDTO){
        centerService.centerRegister(centerDTO);
        return ResponseEntity.status(200).body("Center registered successfully");
    }

    @PutMapping("/update-center")
    public ResponseEntity updateCenter(@AuthenticationPrincipal User user, @Valid @RequestBody CenterDTO centerDTO){
        centerService.updateCenter(user.getId(), centerDTO);
        return ResponseEntity.status(200).body("Center updated successfully");
    }

    @DeleteMapping("/delete-center/{centerid}")
    public ResponseEntity deleteCenter(@PathVariable int centerid, @AuthenticationPrincipal User user){
        centerService.deleteCenter(user.getId(),centerid);
        return ResponseEntity.status(200).body("Center deleted successfully");
    }

    @PostMapping("add-program")
    public ResponseEntity appProgram(@AuthenticationPrincipal User user, @RequestBody @Valid Program program){
        programService.addProgram(user.getCenter(),program);
        return ResponseEntity.status(200).body("Program added successfully");
    }

    @GetMapping("get-my-programs")
    public ResponseEntity getMyPrograms(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(programService.getAllPrograms(user.getCenter()));
    }

    @GetMapping("Center-Account")
    public ResponseEntity showMyCenterAccount(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(centerService.showMyCenterAccount(user.getId()));
    }


    @PutMapping("change-password/{oldpassword}/{newpassword}")
    public ResponseEntity changePassword( @AuthenticationPrincipal User user,@PathVariable String oldpassword, @PathVariable String newpassword){
        centerService.changePassword(user.getId(),oldpassword,newpassword);
        return ResponseEntity.status(200).body("Password changed successfully");
    }
}
