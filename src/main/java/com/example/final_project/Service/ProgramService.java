package com.example.final_project.Service;


import com.example.final_project.Model.Center;
import com.example.final_project.Model.Program;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramService {
    private final ProgramRepository programRepository;

    public List<Program> getAllPrograms(Center center) {
        return programRepository.findProgramByCenter(center);
    }

    public void addProgram(Center center, Program program) {
        program.setCenter(center);
        programRepository.save(program);
    }

    public List<Program> displayAllPrograms() {
        return programRepository.findAll(); //called by parent
    }
}