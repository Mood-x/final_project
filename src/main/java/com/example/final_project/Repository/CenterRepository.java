package com.example.final_project.Repository;

import com.example.final_project.Model.Center;
import com.example.final_project.Model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//Abdulaziz
public interface CenterRepository extends JpaRepository<Center,Integer> {
    Optional<Center> findCenterById(Integer id);
    Center searchCenterById(Integer id);

}
