package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChildProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String currentStage;
    private String progressDetails;
    private LocalDateTime lastUpdate;
    private double attendancePercentage;
    private double progressLevel;
    private String note;
    private Integer completedSessions;
    private Integer totalSessions;;
    private double rating;

    @ManyToOne
    @JoinColumn(name = "child_id")
    @JsonIgnore
    private Child child;

    @ManyToOne
    @JoinColumn(name = "program_id")
    @JsonIgnore
    private Program program;
}
