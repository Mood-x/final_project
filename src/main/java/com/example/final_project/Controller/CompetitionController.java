package com.example.final_project.Controller;

import com.example.final_project.API.ApiResponse;
import com.example.final_project.Model.Competition;
import com.example.final_project.Model.User;
import com.example.final_project.Service.CompetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/competition")
@RequiredArgsConstructor
public class CompetitionController {
    private final CompetitionService competitionService;

    @GetMapping("/get-all-competitions")
    public ResponseEntity<List<Competition>> getAllCompetitions() {
        return ResponseEntity.ok(competitionService.getAllCompetitions());
    }

    @GetMapping("/get-competition-by-id/{competitionId}")
    public ResponseEntity<Competition> getCompetitionsById(@PathVariable Integer competitionId) {
        return ResponseEntity.ok(competitionService.getCompetitionById(competitionId));
    }

    @PostMapping("/add-competition")
    public ResponseEntity<ApiResponse> addCompetition(@RequestBody Competition competition) {
        competitionService.addCompetition(competition);
        return ResponseEntity.ok(new ApiResponse("Competition added successfully"));
    }

    @PutMapping("/update-competition/{competitionId}")
    public ResponseEntity<ApiResponse> updateCompetition(@PathVariable Integer competitionId, @RequestBody Competition competition) {
        competitionService.updateCompetition(competitionId, competition);
        return ResponseEntity.ok(new ApiResponse("Competition updated successfully"));
    }

    @DeleteMapping("/delete-competition/{competitionId}")
    public ResponseEntity<ApiResponse> deleteCompetition(@PathVariable Integer competitionId) {
        competitionService.deleteCompetition(competitionId);
        return ResponseEntity.ok(new ApiResponse("Competition deleted successfully"));
    }

    @PostMapping("/add-child-to-competition/{competitionId}/{childId}")
    public ResponseEntity<ApiResponse> addChildToCompetition(@AuthenticationPrincipal User user, @PathVariable Integer competitionId, @PathVariable Integer childId) {
        competitionService.addChildToCompetition(user.getId(), competitionId, childId);
        return ResponseEntity.ok(new ApiResponse("Child added successfully"));
    }

    @GetMapping("/search-by-date/{startDate}/{endDate}")
    public ResponseEntity<List<Competition>> searchCompetitionByDate(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        return ResponseEntity.ok(competitionService.searchCompetitionsByDateRange(startDate, endDate));
    }

    @GetMapping("/search-competition-by-name/{name}")
    public ResponseEntity<Competition> searchCompetitionByName(@PathVariable String name) {
        return ResponseEntity.ok(competitionService.searchCompetitionByName(name));
    }

    @GetMapping("/search-competitions-by-type/{type}")
    public ResponseEntity<List<Competition>> searchCompetitionsByType(@PathVariable String type) {
        return ResponseEntity.ok(competitionService.searchCompetitionsByType(type));
    }
}
