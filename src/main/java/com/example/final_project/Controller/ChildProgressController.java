package com.example.final_project.Controller;

import com.example.final_project.API.ApiResponse;
import com.example.final_project.Model.ChildProgress;
import com.example.final_project.Model.User;
import com.example.final_project.Service.ChildProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/progress")
@RequiredArgsConstructor
public class ChildProgressController {
    private final ChildProgressService childProgressService;

    @GetMapping("/get-all-child-progerss")
    public ResponseEntity<List<ChildProgress>> getAllChildProgress(){
        return ResponseEntity.ok(childProgressService.getAllChildProgress());
    }

    @GetMapping("/get-progress-by-program-id/{programId}")
    public ResponseEntity<List<ChildProgress>> getChildProgressByProgramId(@PathVariable int programId){
        return ResponseEntity.ok(childProgressService.getChildProgressByProgramId(programId));
    }

    @GetMapping("/get-all-child-progress-by-child-id/{childId}")
    public ResponseEntity<List<ChildProgress>> getAllChildProgressByChildId(@PathVariable int childId){
        return ResponseEntity.ok(childProgressService.getAllChildProgressByChildId(childId));
    }

    @PostMapping("/create-child-progress")
    public ResponseEntity<ApiResponse> createChildProgress(@AuthenticationPrincipal User user, @RequestBody ChildProgress childProgress){
        childProgressService.createChildProgress(user.getId(), childProgress);
        return ResponseEntity.ok(new ApiResponse("Added child progress successfully"));
    }

    @PutMapping("/modify-child-progress/{progressId}")
    public ResponseEntity<ApiResponse> modifyChildProgress(@AuthenticationPrincipal User user, @PathVariable int progressId, @RequestBody ChildProgress childProgress){
        childProgressService.updateChildProgress(user.getId(), progressId, childProgress);
        return ResponseEntity.ok(new ApiResponse("Modified child progress successfully"));
    }

    @DeleteMapping("/delete-child-progress/{progressId}")
    public ResponseEntity<ApiResponse> deleteChildProgress(@AuthenticationPrincipal User user, @PathVariable int progressId){
        childProgressService.deleteChildProgress(user.getId(), progressId);
        return ResponseEntity.ok(new ApiResponse("Deleted child progress successfully"));
    }

    @PostMapping("/calculate-child-progress-metrics")
    public ResponseEntity<ApiResponse> calculateChildProgressMetrics(@AuthenticationPrincipal User user){
        childProgressService.calculateProgressMetrics(user.getId());
        return ResponseEntity.ok(new ApiResponse("Calculated child progress metrics successfully"));
    }
}
