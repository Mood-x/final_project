package com.example.final_project.Repository;


import com.example.final_project.Model.Center;
import com.example.final_project.Model.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Integer> {

    Optional<Program> findProgramById(Integer id);

    List<Program> findProgramByCenter(Center center);
    List<Program> findByTitleContainingIgnoreCase(String title);

}
