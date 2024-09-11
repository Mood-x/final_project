package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "parent")
    private Set<Child> children;

    @OneToMany(mappedBy = "parent")
    private Set<Like> likes;

    @OneToMany(mappedBy = "parent")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "parent")
    private Set<Rating> ratings;

    @OneToMany(mappedBy = "parent")
    private Set<Complaint> complaints;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;
}
