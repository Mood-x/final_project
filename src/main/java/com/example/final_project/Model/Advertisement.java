package com.example.final_project.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Title cannot be Empty!")
    @Size(min = 5,max = 30,message = "Title length must be more than '5' and less than '30'!")
    @Column(columnDefinition = "varchar(30) not null")
    private String title;

    @NotEmpty(message = "Description cannot be Empty!")
    @Size(min = 20,max = 550,message = "Description length must be more than '15' and less than '550'!")
    @Column(columnDefinition = "varchar(550) not null")
    private String description;

    @NotNull(message = "Days duration should not be empty!")
    @Positive(message = "days duration should be positive")
    //@Max()
    private int daysDuration;
}
