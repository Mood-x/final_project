package com.example.final_project.Model;

import lombok.Getter;
import lombok.Setter;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Program {
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

    @NotNull(message = "price should not be empty!")
    @PositiveOrZero(message = "price should be positive number or zero!")
    @Column(columnDefinition = "double not null")
    private double price;

    @NotNull(message = "capacity should not be empty!")
    @Positive(message = "capacity should be positive number!")
    @Column(columnDefinition = "int not null")
    private int capacity;

    @NotNull(message = "age should not be empty!")
    @Positive(message = "age should be positive number!")
    @Column(columnDefinition = "int not null")
    private int age;

    @NotEmpty(message = "Address cannot be Empty!")
    @Size(min = 2,max = 40,message = "Address length must be more than '4' and less than '40'!")
    @Column(columnDefinition = "varchar(40) not null")
    private String address;

    @Pattern(regexp = "^(open|close)$")
    @Column(columnDefinition = "varchar(15)")
    private String status = "open";

    @NotNull(message = "StartDate should be not empty!")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date not null")
    private Date startDate;

    @NotNull(message = "EndDate should be not empty!")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date not null")
    private Date endDate;


    //====================== Relations ======================

    @ManyToOne
    @JsonIgnore
    private Center center;
}
