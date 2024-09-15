package com.example.final_project.Controller;

import com.example.final_project.API.ApiResponse;
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

    @GetMapping("/Center-Account")
    public ResponseEntity showMyCenterAccount(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(centerService.showMyCenterAccount(user.getId()));
    }

    @PutMapping("/change-password/{oldpassword}/{newpassword}")
    public ResponseEntity changePassword( @AuthenticationPrincipal User user,@PathVariable String oldpassword, @PathVariable String newpassword){
        centerService.changePassword(user.getId(),oldpassword,newpassword);
        return ResponseEntity.status(200).body("Password changed successfully");
    }


    @GetMapping("/display-total-center-financial-returns")
    public ResponseEntity displayCenterFinancialReturn(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(centerService.displayCenterFinancialReturns(user.getId()));
    }

    @GetMapping("/display-total-number-of-joind-childrens")
    public ResponseEntity displayTotalNumberOfJoindChildren(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(centerService.displayCenterNumberOfChild(user.getId()));
    }

    @GetMapping("/display-total-number-center-program")
    public ResponseEntity displayTotalNumberOfCenterProgram(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(centerService.getTotalNumberOfCenterPrograms(user.getId()));
    }

    // [ Mohammed ] +[End-Point]
    @PutMapping("/approve-center-registration/{centerId}")
    public ResponseEntity<ApiResponse> approveCenterRegistration(@PathVariable Integer centerId) {
        centerService.approveCenterRegistration(centerId);
        return ResponseEntity.ok(new ApiResponse("Notification approved successfully"));
    }

    // [ Mohammed ] +[End-Point]
    @PutMapping("/reject-center-registration/{centerId}/{rejectionReason}")
    public ResponseEntity<ApiResponse> rejectCenterRegistration(@PathVariable Integer centerId, @PathVariable String rejectionReason) {
        centerService.rejectCenterRegistration(centerId, rejectionReason);
        return ResponseEntity.ok(new ApiResponse("Notification rejected successfully"));
    }
}
