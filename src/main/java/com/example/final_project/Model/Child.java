package com.example.final_project.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Child {
    //YARA
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name cannot be blank")
    @Column(nullable = false)
    private String name;

    @Column(name = "pic_url")
    private String picUrl;

    @NotNull(message = "Age cannot be null")
    @Column(nullable = false)
    private Integer age;

    @NotNull(message = "Join date cannot be null")
    @Column(nullable = false)
    private LocalDate joinDate;

    @NotBlank(message = "Gender cannot be blank")
    @Pattern(regexp = "^(male|female)$", message = "Gender must be either 'male' or 'female'")
    @Column(nullable = false)
    private String gender; // Gender validation

    //RELATION
    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    @JsonIgnore
    private Parent parent; // Reference to Parent

    @ManyToMany
    @JoinTable(name = "child_program_subscription", joinColumns = @JoinColumn(name = "child_id"), inverseJoinColumns = @JoinColumn(name = "program_id"))
    private Set<Program> programs; // Many-to-many relation with Program

    @ManyToMany(mappedBy = "participants")
    private Set<Competition> competitions;

//    @OneToOne(mappedBy = "child", cascade = CascadeType.ALL)
//    private ChildProgress childProgress;
}