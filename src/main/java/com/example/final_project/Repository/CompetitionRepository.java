package com.example.final_project.Repository;

import com.example.final_project.Model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Integer> {
    Optional<Competition> findCompetitionById(Integer id);
    Optional<Competition> findCompetitionByName(String name);

}
