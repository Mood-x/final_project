package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Parent ID cannot be null")
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent; // Reference to Parent

    @NotNull(message = "Center ID cannot be null")
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "center_id", nullable = false)
    private Center center; // Reference to Center

    @NotBlank(message = "Content cannot be blank")
    @Column(nullable = false)
    private String content;

    @NotNull(message = "Creation date cannot be null")
    @Column(name = "created_at", nullable = false)
    private Date createdAt; // Creation date
}


