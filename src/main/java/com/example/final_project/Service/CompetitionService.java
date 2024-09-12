package com.example.final_project.Service;

import com.example.final_project.API.ApiException;
import com.example.final_project.Model.Competition;
import com.example.final_project.Repository.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompetitionService {
    private final CompetitionRepository competitionRepository;

    public List<Competition> getAllCompetitions(){
        return competitionRepository.findAll();
    }

    public Competition getCompetitionById(Integer id){
        return competitionRepository.findCompetitionById(id)
                .orElseThrow(() -> new ApiException("Competition not found"));
    }

    public Competition getCompetitionByName(String name){
        return competitionRepository.findCompetitionByName(name)
                .orElseThrow(() -> new ApiException("Competition not found"));
    }

    public void addCompetition(Competition competition){
        competitionRepository.save(competition);
    }

    public void updateCompetition(Integer id, Competition updateCompetition){
        Competition competition = getCompetitionById(id);
        competition.setName(updateCompetition.getName());
        competition.setStartDate(updateCompetition.getStartDate());
        competition.setEndDate(updateCompetition.getEndDate());
        competition.setParticipant(updateCompetition.getParticipant());
        competitionRepository.save(competition);
    }

    public void deleteCompetition(Integer id){
        Competition competition = competitionRepository.findCompetitionById(id)
                .orElseThrow(() -> new ApiException("Competition not found"));
        competitionRepository.delete(competition);
    }
}
