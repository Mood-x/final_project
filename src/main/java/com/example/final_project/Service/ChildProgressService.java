package com.example.final_project.Service;

import com.example.final_project.API.ApiException;
import com.example.final_project.Model.Child;
import com.example.final_project.Model.ChildProgress;
import com.example.final_project.Model.Program;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.AuthRepository;
import com.example.final_project.Repository.ChildProgressRepository;
import com.example.final_project.Repository.ChildRepository;
import com.example.final_project.Repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChildProgressService {
    private final ChildProgressRepository childProgressRepository;
    private final ProgramRepository programRepository;
    private final ChildRepository childRepository;
    private final AuthRepository authRepository;

    public List<ChildProgress> getAllChildProgress(){
        return childProgressRepository.findAll();
    }

    public List<ChildProgress> getChildProgressByProgramId(Integer program_id){
        return childProgressRepository.findAllByProgramId(program_id).orElseThrow(() -> new ApiException("No progress found for this program"));
    }

    public List<ChildProgress> getAllChildProgressByChildId(Integer childId){
       return childProgressRepository.findAllByChildId(childId).orElseThrow(() -> new ApiException("No progress found for this child"));
    }

    public void createChildProgress(Integer authId, ChildProgress childProgress){
        Program program = programRepository.findProgramById(childProgress.getProgram().getId()).orElseThrow(() -> new ApiException("Not found program"));
        Child child = childRepository.findChildById(childProgress.getChild().getId()).orElseThrow(() -> new ApiException("Not found child"));
        User center = authRepository.findUserById(authId).orElseThrow(() -> new ApiException("Not found center"));

        if(!program.getCenter().equals(center.getCenter())){
            throw new ApiException("You don't have permission to modify this child progress");
        }
        childProgressRepository.save(childProgress);
    }

    public void updateChildProgress(Integer authId, Integer progressId, ChildProgress updateChildProgress){
        ChildProgress childProgress = childProgressRepository.findChildProgressById(progressId).orElseThrow(() -> new ApiException("Not found progress"));
        Program program = programRepository.findProgramById(childProgress.getProgram().getId()).orElseThrow(() -> new ApiException("Not found program"));
        User center = authRepository.findUserById(authId).orElseThrow(() -> new ApiException("Not found center"));

        if(!program.getCenter().equals(center.getCenter())){
            throw new ApiException("You don't have permission to modify this child progress");
        }

        childProgress.setCurrentStage(updateChildProgress.getCurrentStage());
        childProgress.setProgressDetails(updateChildProgress.getProgressDetails());
        childProgress.setProgressLevel(updateChildProgress.getProgressLevel());
        childProgress.setAttendancePercentage(updateChildProgress.getAttendancePercentage());
        childProgress.setCompletedSessions(updateChildProgress.getCompletedSessions());
        childProgress.setTotalSessions(updateChildProgress.getTotalSessions());
        childProgressRepository.save(childProgress);
    }

    public void deleteChildProgress(Integer authId, Integer progressId){
        ChildProgress childProgress = childProgressRepository.findChildProgressById(progressId).orElseThrow(() -> new ApiException("Not found progress"));
        Program program = programRepository.findProgramById(childProgress.getProgram().getId()).orElseThrow(() -> new ApiException("Not found program"));
        User center = authRepository.findUserById(authId).orElseThrow(() -> new ApiException("Not found center"));
        if(!program.getCenter().equals(center.getCenter())){
            throw new ApiException("You don't have permission to modify this child progress");
        }
        childProgressRepository.delete(childProgress);
    }

    public void calculateProgressMetrics(Integer authId){
        ChildProgress childProgress = childProgressRepository.findChildProgressById(authId).orElseThrow(() -> new ApiException("Not found progress"));
        double rating = (double) childProgress.getCompletedSessions() / childProgress.getTotalSessions();
        childProgress.setRating(rating);

        childProgressRepository.save(childProgress);
    }
}
