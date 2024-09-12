package com.example.final_project.Controller;

import com.example.final_project.API.ApiResponse;
import com.example.final_project.Model.Competition;
import com.example.final_project.Service.CompetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get-competition-by-id")
    public ResponseEntity<Competition> getCompetitionsById(@RequestParam Integer id) {
        return ResponseEntity.ok(competitionService.getCompetitionById(id));
    }

    @GetMapping("/get-competition-by-name")
    public ResponseEntity<Competition> getCompetitionByName(@RequestParam String name) {
        return ResponseEntity.ok(competitionService.getCompetitionByName(name));
    }

    @PostMapping("/add-competition")
    public ResponseEntity<ApiResponse> addCompetition(@RequestBody Competition competition) {
        competitionService.addCompetition(competition);
        return ResponseEntity.ok(new ApiResponse("Competition added successfully"));
    }

    @PutMapping("/update-competition")
    public ResponseEntity<ApiResponse> updateCompetition(@RequestParam Integer id, @RequestBody Competition competition) {
        competitionService.updateCompetition(id, competition);
        return ResponseEntity.ok(new ApiResponse("Competition updated successfully"));
    }

    @DeleteMapping("/delete-competition")
    public ResponseEntity<ApiResponse> deleteCompetition(@RequestParam Integer id) {
        competitionService.deleteCompetition(id);
        return ResponseEntity.ok(new ApiResponse("Competition deleted successfully"));
    }
}
