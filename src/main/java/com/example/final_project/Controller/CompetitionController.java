package com.example.final_project.Controller;

import com.example.final_project.API.ApiResponse;
import com.example.final_project.Model.Child;
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

    @GetMapping("/search-competitions-by-age-range/{ageFrom}/{ageTo}")
    public ResponseEntity<List<Competition>> searchCompetitionsByAgeRange(@PathVariable Integer ageFrom, @PathVariable Integer ageTo) {
        return ResponseEntity.ok(competitionService.searchCompetitionsByAgeRange(ageFrom, ageTo));
    }

    @GetMapping("/get-all-competitions-for-child/{childId}")
    public ResponseEntity<List<Competition>> getAllCompetitionsForChild(@PathVariable Integer childId) {
        return ResponseEntity.ok(competitionService.getAllCompetitionsForChild(childId));
    }

    @PostMapping("/participation-request/{competitionId}/{childId}")
    public ResponseEntity<ApiResponse> participationRequest(@AuthenticationPrincipal User user, @PathVariable Integer competitionId, @PathVariable Integer childId) {
        competitionService.participationRequest(user.getId(), competitionId, childId);
        return ResponseEntity.ok(new ApiResponse("Participation request added successfully"));
    }

    @PostMapping("/approve-participation-request/{competitionId}/{childId}/{parentId}")
    public ResponseEntity<ApiResponse> approvedParticipationRequest(@PathVariable Integer competitionId, @PathVariable Integer childId, @PathVariable Integer parentId) {
        competitionService.approvedParticipationRequest(competitionId, childId, parentId);
        return ResponseEntity.ok(new ApiResponse("Participation request approved successfully"));
    }

    @PostMapping("/reject-participation-request/{competitionId}/{childId}/{parentId}")
    public ResponseEntity<ApiResponse> rejectParticipationRequest(@PathVariable Integer competitionId, @PathVariable Integer childId, @PathVariable Integer parentId) {
        competitionService.rejectParticipationRequest(competitionId, childId, parentId);
        return ResponseEntity.ok(new ApiResponse("Participation request rejected successfully"));
    }

    @DeleteMapping("/cancel-child-participation/{competitionId}/{childId}")
    public ResponseEntity<ApiResponse> cancelParticipation(@AuthenticationPrincipal User user, @PathVariable Integer competitionId, @PathVariable Integer childId) {
        competitionService.cancelChildParticipation(user.getId(), competitionId, childId);
        return ResponseEntity.ok(new ApiResponse("Participation request cancelled successfully"));
    }

}
