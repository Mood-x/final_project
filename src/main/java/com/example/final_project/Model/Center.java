package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity


public class Center {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Phone number should not be empty!")
    @Size(min = 10,max = 10,message = "teacher phone number should be '10' digits")
    @Pattern(regexp = "^05\\d*$",message = "Phone number must start with '05' !")
    private String phoneNumber;


    @NotEmpty(message = "Address cannot be Empty!")
    @Size(min = 2,max = 40,message = "Address length must be more than '4' and less than '40'!")
    @Column(columnDefinition = "varchar(40) not null")
    private String address;

    private String documents = "Licences";

    @NotEmpty(message = "Activity Type cannot be Empty!")
    @Pattern(regexp = "^(Sport|Academic|Cultural|Draw)$")
    @Column(columnDefinition = "varchar(15) not null")
    private String activityType;


    //====================== Relations ======================

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

}
