package com.example.final_project.Service;

import com.example.final_project.API.ApiException;
import com.example.final_project.Model.Child;
import com.example.final_project.Model.Competition;
import com.example.final_project.Model.Parent;
import com.example.final_project.Repository.ChildRepository;
import com.example.final_project.Repository.CompetitionRepository;
import com.example.final_project.Repository.ParentReposotiry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompetitionService {
    private final CompetitionRepository competitionRepository;
    private final ChildRepository childRepository;
    private final ParentReposotiry parentReposotiry;

    public List<Competition> getAllCompetitions(){
        return competitionRepository.findAll();
    }

    public Competition getCompetitionById(Integer id){
        return competitionRepository.findCompetitionById(id)
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

    public void addChildToCompetition(Integer authId, Integer competitionId, Integer childId) {
        Competition competition = getCompetitionById(competitionId);

        Parent parent = parentReposotiry.findParentById(authId)
                .orElseThrow(() -> new ApiException("Parent not found"));

        Child child = childRepository.findChildById(childId)
                .orElseThrow(() -> new ApiException("Child not found"));

        if(!parent.getChildren().contains(child)){
            throw new ApiException("Child not found");
        }

        competition.setParticipant(competition.getParticipant() + 1);
        competition.getChildren().add(child);
        child.getCompetitions().add(competition);
        childRepository.save(child);
        competitionRepository.save(competition);
    }

    public List<Competition> searchCompetitionsByDateRange(LocalDate startDate, LocalDate endDate){
        return competitionRepository.findAllByDateBetween(startDate, endDate).orElseThrow(() -> new ApiException("Not found competitions between this date"));
    }

    public Competition searchCompetitionByName(String name){
        return competitionRepository.findCompetitionByName(name).orElseThrow(() -> new ApiException("Competition not found"));
    }

    public List<Competition> searchCompetitionsByType(String type){
        return competitionRepository.findAllByType(type).orElseThrow(() -> new ApiException("Not found competitions by this type"));
    }

    public List<Competition> searchCompetitionsByAgeRange(Integer ageFrom, Integer ageTo){
        return competitionRepository.findCompetitionsByAgeRange(ageFrom, ageTo).orElseThrow(() -> new ApiException("Not found competitions by this age range"));
    }
}


