package com.example.final_project.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Parent cannot be null")
    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent; // Reference to Parent

    @NotNull(message = "Center cannot be null")
    @ManyToOne
    @JoinColumn(name = "center_id", nullable = false)
    private Center center; // Reference to Center

    @NotBlank(message = "Content cannot be blank")
    @Column(nullable = false)
    private String content; // Content of the complaint
}